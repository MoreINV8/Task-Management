package ku.cs.task_management.requests.notification_requests;

import ku.cs.task_management.commons.NotificationType;
import ku.cs.task_management.entities.Member;
import ku.cs.task_management.services.NotificationService;
import lombok.Data;

import java.util.UUID;

@Data
public class NotificationSendRequest {
    private NotificationType type;

    private UUID objectId;

    private Member receiver;

    public static NotificationSendRequest project(UUID projectId) {
        NotificationSendRequest n = new NotificationSendRequest();
        n.setObjectId(projectId);
        n.setType(NotificationType.PROJECT);

        return n;
    }

    public static NotificationSendRequest task(UUID taskId) {
        NotificationSendRequest n = new NotificationSendRequest();
        n.setObjectId(taskId);
        n.setType(NotificationType.TASK);

        return n;
    }

    public static NotificationSendRequest meeting(UUID meetingId) {
        NotificationSendRequest n = new NotificationSendRequest();
        n.setObjectId(meetingId);
        n.setType(NotificationType.MEETING);

        return n;
    }
}
