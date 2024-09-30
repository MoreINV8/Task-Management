package ku.cs.task_management.requests.task_requests;

import lombok.Data;

import java.util.UUID;

@Data
public class TaskUpdateRequest {
    private UUID taskId;
    private String taskName;
    private String taskDetail;
    private String taskStatus;
    private UUID taskProjectId;
}
