package ku.cs.task_management.requests.task_requests;

import ku.cs.task_management.commons.TaskStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class TaskUpdateRequest {
    private UUID taskId;
    private String taskName;
    private String taskDetail;
    private TaskStatus taskStatus;
    private UUID taskProjectId;
}
