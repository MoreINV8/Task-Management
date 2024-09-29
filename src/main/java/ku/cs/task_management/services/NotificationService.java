package ku.cs.task_management.services;

import ku.cs.task_management.configs.NotificationStatus;
import ku.cs.task_management.configs.NotificationType;
import ku.cs.task_management.entities.Notification;
import ku.cs.task_management.exceptions.InvalidNotificationTypeException;
import ku.cs.task_management.exceptions.InvalidRequestException;
import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.repositories.*;
import ku.cs.task_management.requests.notification_requests.NotificationSendRequest;
import ku.cs.task_management.responses.NotificationResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;

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
        NotificationResponse response = modelMapper.map(notificationRepository.save(notification), NotificationResponse.class);

        //TODO: ถ้าทำเสร็จแล้วอย่ากส่งเป็น object ออกไปเลยค่อยมาแก้
        response.setReceiverId(notification.getReceiver().getMemberId());
        response.setType(request.getType());

        switch (request.getType()) {
            case (1) -> response.setProjectId(notification.getProject().getProjectId());
            case (2) -> response.setTaskId(notification.getTask().getTaskId());
            case (3) -> response.setMeetingId(notification.getMeeting().getMeetingId());
        }

        return response;
    }
}
