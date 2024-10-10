package ku.cs.task_management.requests.notification_requests;

import ku.cs.task_management.commons.NotificationType;
import lombok.Data;

import java.util.UUID;

@Data
public class NotificationSendRequest {
    private String notificationDetail;

    private NotificationType type;

    private UUID projectId;
    private UUID taskId;
    private UUID meetingId;

    private UUID receiverId;

    public int isTypeMatch() {
        if (type.equals(NotificationType.PROJECT) && projectId != null) return NotificationType.PROJECT.ordinal();
        else if (type.equals(NotificationType.TASK) && taskId != null) return NotificationType.TASK.ordinal();
        else if (type.equals(NotificationType.MEETING) && meetingId != null) return NotificationType.MEETING.ordinal();
        return -1;
    }
}
