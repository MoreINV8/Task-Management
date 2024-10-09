package ku.cs.task_management.services;

import ku.cs.task_management.configs.NotificationStatus;
import ku.cs.task_management.configs.NotificationType;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
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
            throws NotFoundMemberException, InvalidNotificationTypeException, InvalidRequestException {
        // check if member account was existed
        if (memberRepository.findMemberByMemberId(request.getReceiverId()) == null) {
            throw new NotFoundMemberException(memberRepository.findMemberEmailByMemberId(request.getReceiverId()));
        }

        // check if request type is valid
        if (request.getType() < NotificationType.PROJECT
                && request.getType() > NotificationType.MEETING) {
            throw new InvalidNotificationTypeException();
        }

        // check if id of project, task, meeting once is not null
        if (request.getType() == 1 && request.getProjectId() == null) {
            throw new InvalidRequestException();
        }
        if (request.getType() == 2 && request.getTaskId() == null) {
            throw new InvalidRequestException();
        }
        if (request.getType() == 3 && request.getMeetingId() == null) {
            throw new InvalidRequestException();
        }

        // retrieve data from request
        Notification notification = new Notification();
        notification.setNotificationDetail(request.getNotificationDetail());

        // set value
        LocalDate currentTime = LocalDate.now();
        notification.setNotificationTime(Date.valueOf(currentTime));
        notification.setNotificationStatus(NotificationStatus.UNREAD);
        notification.setReceiver(memberRepository.findMemberByMemberId(request.getReceiverId()));

        switch (request.getType()) {
            case (1) -> notification.setProject(projectRepository.getReferenceById(request.getProjectId()));
            case (2) -> notification.setTask(taskRepository.getReferenceById(request.getTaskId()));
            case (3) -> notification.setMeeting(meetingRepository.getReferenceById(request.getMeetingId()));
        }

        // get response
        return getResponse(notificationRepository.save(notification));
    }

    public List<NotificationResponse> getNotificationFromMemberId(String mId)
            throws NotFoundMemberException, IllegalArgumentException {
        // convert string to uuid
        UUID receiverId = UUID.fromString(mId);

        // check if member account existed
        if (memberRepository.findMemberByMemberId(receiverId) == null) {
            throw new NotFoundMemberException(memberRepository.findMemberEmailByMemberId(receiverId));
        }

        // retrieve notifications of this member
        List<NotificationResponse> notifications = new ArrayList<>();
        for (Notification notification : notificationRepository.getNotificationsByReceiverId(receiverId)) {
            notifications.add(getResponse(notification));
        }

        return notifications;
    }

    public NotificationResponse readNotification(String nId)
            throws NotFoundNotificationException, IllegalArgumentException {
        // convert string to uuid
        UUID notificationId = UUID.fromString(nId);

        // check if notification existed
        if (!notificationRepository.existsById(notificationId)) {
            throw new NotFoundNotificationException(nId);
        }

        // set notification status to read
        Notification notification = notificationRepository.getReferenceById(notificationId);
        notification.setNotificationStatus(NotificationStatus.READ);

        // save notification to database
        return getResponse(notificationRepository.save(notification));
    }

    public SuccessResponse deleteNotification(String nId)
            throws NotFoundNotificationException, IllegalArgumentException {
        UUID notificationId = UUID.fromString(nId);

        if (!notificationRepository.existsById(notificationId)) {
            throw new NotFoundNotificationException(nId);
        }

        Notification notification = notificationRepository.getReferenceById(notificationId);
        notification.setProject(null);
        notification.setTask(null);
        notification.setMeeting(null);
        notification.setReceiver(null);

        notificationRepository.save(notification);

        notificationRepository.delete(notification);

        return new SuccessResponse("success full delete notification id '" + nId + "'", HttpStatus.ACCEPTED);
    }

    private NotificationResponse getResponse(Notification notification) {
        // formatting response
        NotificationResponse response = modelMapper.map(notification, NotificationResponse.class);

        response.setReceiverId(notification.getReceiver().getMemberId());

        // TODO: ถ้าทำเสร็จแล้วอย่ากส่งเป็น object ออกไปเลยค่อยมาแก้
        if (notification.getProject() != null) {
            response.setType(1);
            response.setProjectId(notification.getProject().getProjectId());
        }
        if (notification.getTask() != null) {
            response.setType(2);
            response.setTaskId(notification.getTask().getTaskId());
        }
        if (notification.getMeeting() != null) {
            response.setType(3);
            response.setMeetingId(notification.getMeeting().getMeetingId());
        }

        return response;
    }
}
