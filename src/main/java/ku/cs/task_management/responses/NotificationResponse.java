package ku.cs.task_management.responses;

import ku.cs.task_management.commons.NotificationStatus;
import ku.cs.task_management.commons.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class NotificationResponse {
    private UUID notificationId;

    private LocalDateTime notificationTime;
    private String notificationDetail;

    // notification status { 0:"unread", 1:"read" }
    private NotificationStatus notificationStatus;

    // notification from { 1:"project", 2:"task", 3:"meeting" }
    private NotificationType type;

    private UUID projectId;
    private UUID taskId;
    private UUID meetingId;

    private UUID receiverId;
}
