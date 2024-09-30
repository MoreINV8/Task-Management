package ku.cs.task_management.requests.task_requests;

import ku.cs.task_management.entities.Project;
import lombok.Data;

import java.util.UUID;

@Data
public class TaskRequest {
    private UUID taskId;
    private String taskName;
    private String taskDescription;
    private UUID taskProjectId;
}
