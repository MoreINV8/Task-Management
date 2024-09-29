package ku.cs.task_management.requests.notification_requests;

import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class NotificationSendRequest {
    private String notificationDetail;

    // notification from { 1:"project", 2:"task", 3:"meeting" }
    private int type;

    private UUID projectId;
    private UUID taskId;
    private UUID meetingId;

    private UUID receiverId;
}
