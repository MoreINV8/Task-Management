package ku.cs.task_management.services;

import ku.cs.task_management.commons.NotificationStatus;
import ku.cs.task_management.commons.NotificationType;
import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.Notification;
import ku.cs.task_management.exceptions.*;
import ku.cs.task_management.repositories.*;
import ku.cs.task_management.requests.notification_requests.NotificationSendRequest;
import ku.cs.task_management.responses.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private ModelMapper modelMapper;

    public NotificationResponse insertNotification(NotificationSendRequest request)
            throws InvalidRequestException, NotFoundProjectException, NotFoundTaskException, NotFoundMeetingException {

        // retrieve data from request
        Notification notification = new Notification();

        // check type
        switch (request.getType()) {
            case PROJECT -> notification.setNotificationProject(
                    projectRepository.findById(request.getObjectId())
                            .orElseThrow(() -> new NotFoundProjectException(request.getObjectId()))
            );
            case TASK -> notification.setNotificationTask(
                    taskRepository.findById(request.getObjectId())
                            .orElseThrow(() -> new NotFoundTaskException(request.getObjectId()))
            );
            case MEETING -> notification.setNotificationMeeting(
                    meetingRepository.findById(request.getObjectId())
                            .orElseThrow(() -> new NotFoundMeetingException(request.getObjectId()))
            );
            default -> throw new InvalidRequestException();
        }

        // set value
        notification.setNotificationTime(LocalDateTime.now());
        notification.setNotificationStatus(NotificationStatus.UNREAD);
        notification.setNotificationReceiver(request.getReceiver());

        // get response
        return getResponse(notificationRepository.save(notification));
    }

    public NotificationResponse insertNotification(NotificationSendRequest request, UUID receiverId) throws NotFoundTaskException, NotFoundProjectException, NotFoundMeetingException, InvalidRequestException, NotFoundMemberException {
        Member receiver = memberRepository.findById(receiverId)
                .orElseThrow(() -> new NotFoundMemberException(receiverId));

        request.setReceiver(receiver);

        return insertNotification(request);
    }

    public NotificationResponse insertNotification(NotificationSendRequest request, String email) throws NotFoundTaskException, NotFoundProjectException, NotFoundMeetingException, InvalidRequestException, NotFoundMemberException {
        Member receiver = memberRepository.findMemberByEmail(email);

        if (receiver == null) {
            throw new NotFoundMemberException(email);
        }

        request.setReceiver(receiver);

        return insertNotification(request);
    }

    public List<NotificationResponse> getNotificationFromMemberId(UUID mId)
            throws NotFoundMemberException, IllegalArgumentException {

        // check if member account existed
        if (!memberRepository.existsById(mId)) {
            throw new NotFoundMemberException(mId);
        }

        // retrieve notifications of this member
        List<NotificationResponse> notifications = new ArrayList<>();
        for (Notification notification : notificationRepository.getNotificationsByReceiverId(mId)) {
            notifications.add(getResponse(notification));
        }

        return notifications;
    }

    public NotificationResponse readNotification(UUID nId)
            throws NotFoundNotificationException, IllegalArgumentException {

        // check if notification existed
        if (!notificationRepository.existsById(nId)) {
            throw new NotFoundNotificationException(nId);
        }

        // set notification status to read
        Notification notification = notificationRepository.findById(nId).get();
        notification.setNotificationStatus(NotificationStatus.READ);

        // save notification to database
        return getResponse(notificationRepository.save(notification));
    }

    public SuccessResponse deleteNotification(UUID nId)
            throws NotFoundNotificationException, IllegalArgumentException {

        if (!notificationRepository.existsById(nId)) {
            throw new NotFoundNotificationException(nId);
        }

        Notification notification = notificationRepository.getReferenceById(nId);
        notification.setNotificationProject(null);
        notification.setNotificationTask(null);
        notification.setNotificationMeeting(null);
        notification.setNotificationReceiver(null);

        notificationRepository.save(notification);

        notificationRepository.delete(notification);

        return new SuccessResponse("success full delete notification id '" + nId + "'", HttpStatus.ACCEPTED);
    }

    private NotificationResponse getResponse(Notification notification) {
        // formatting response
        NotificationResponse response = modelMapper.map(notification, NotificationResponse.class);

        if (notification.getNotificationProject() != null) {
            response.setType(NotificationType.PROJECT);
            response.setProject(new ProjectResponse(notification.getNotificationProject()));
        }
        if (notification.getNotificationTask() != null) {
            response.setType(NotificationType.TASK);
            response.setTask(new TaskResponse(notification.getNotificationTask()));
        }
        if (notification.getNotificationMeeting() != null) {
            response.setType(NotificationType.MEETING);
            response.setMeeting(new MeetingResponse(notification.getNotificationMeeting()));
        }

        return response;
    }
}
