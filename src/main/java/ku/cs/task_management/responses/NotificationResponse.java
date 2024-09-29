package ku.cs.task_management.responses;

import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class NotificationResponse {
    private UUID notificationId;

    private Date notificationTime;
    private String notificationDetail;

    // notification status { 0:"unread", 1:"read" }
    private int notificationStatus;

    // notification from { 1:"project", 2:"task", 3:"meeting" }
    private int type;

    private UUID projectId;
    private UUID taskId;
    private UUID meetingId;

    private UUID receiverId;
}
