package ku.cs.task_management.utils;

import ku.cs.task_management.entities.Notification;
import ku.cs.task_management.exceptions.NotFoundMeetingException;
import ku.cs.task_management.repositories.MeetingRepository;

import java.util.UUID;

public class MeetingNotificationHandler implements NotificationHandler {
    private final MeetingRepository meetingRepository;

    public MeetingNotificationHandler(MeetingRepository meetingRepository) {
        this.meetingRepository = meetingRepository;
    }

    @Override
    public void handle(Notification notification, UUID objectId) throws NotFoundMeetingException {
        notification.setNotificationMeeting(
                meetingRepository.findById(objectId)
                        .orElseThrow(() -> new NotFoundMeetingException(objectId))
        );
    }
}
