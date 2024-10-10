package ku.cs.task_management.services;

import ku.cs.task_management.commons.NotificationStatus;
import ku.cs.task_management.commons.NotificationType;
import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.Notification;
import ku.cs.task_management.exceptions.InvalidNotificationTypeException;
import ku.cs.task_management.exceptions.InvalidRequestException;
import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.exceptions.NotFoundNotificationException;
import ku.cs.task_management.repositories.*;
import ku.cs.task_management.requests.notification_requests.NotificationSendRequest;
import ku.cs.task_management.responses.NotificationResponse;
import ku.cs.task_management.responses.SuccessResponse;
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
            throws NotFoundMemberException, InvalidRequestException {

        // check if member account was existed
        Member receiver = memberRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new NotFoundMemberException(request.getReceiverId()));

        // retrieve data from request
        Notification notification = new Notification();

        // check type
        switch (request.isTypeMatch()) {
            case (0) -> notification.setNotificationProject(projectRepository.getReferenceById(request.getProjectId()));
            case (1) -> notification.setNotificationTask(taskRepository.getReferenceById(request.getTaskId()));
            case (2) -> notification.setNotificationMeeting(meetingRepository.getReferenceById(request.getMeetingId()));
            default -> throw new InvalidRequestException();
        }

        notification.setNotificationDetail(request.getNotificationDetail());

        // set value
        notification.setNotificationTime(LocalDateTime.now());
        notification.setNotificationStatus(NotificationStatus.UNREAD);
        notification.setNotificationReceiver(receiver);

        // get response
        return getResponse(notificationRepository.save(notification));
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

        response.setReceiverId(notification.getNotificationReceiver().getMemberId());

        // TODO: ถ้าทำเสร็จแล้วอย่ากส่งเป็น object ออกไปเลยค่อยมาแก้
        if (notification.getNotificationProject() != null) {
            response.setType(NotificationType.PROJECT);
            response.setProjectId(notification.getNotificationProject().getProjectId());
        }
        if (notification.getNotificationTask() != null) {
            response.setType(NotificationType.TASK);
            response.setTaskId(notification.getNotificationTask().getTaskId());
        }
        if (notification.getNotificationMeeting() != null) {
            response.setType(NotificationType.MEETING);
            response.setMeetingId(notification.getNotificationMeeting().getMeetingId());
        }

        return response;
    }
}
