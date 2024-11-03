package ku.cs.task_management.requests.task_requests;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TaskCreateRequest {
    private String taskName;
    private String taskDetail;
    private UUID taskProjectId;
    private UUID taskOwnerId;
    private LocalDateTime taskDueDate;
}
