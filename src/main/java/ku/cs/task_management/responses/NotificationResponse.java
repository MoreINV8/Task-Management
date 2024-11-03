package ku.cs.task_management.responses;

import ku.cs.task_management.commons.NotificationStatus;
import ku.cs.task_management.commons.NotificationType;
import ku.cs.task_management.entities.Project;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class NotificationResponse {
    private UUID notificationId;

    private LocalDateTime notificationTime;
    private String notificationDetail;

    private NotificationStatus notificationStatus;

    private NotificationType type;

    private ProjectResponse project;
    private TaskResponse task;
    private MeetingResponse meeting;
}
