package ku.cs.task_management.responses;

import ku.cs.task_management.commons.TaskStatus;
import ku.cs.task_management.entities.Project;
import lombok.Data;

import java.util.UUID;

@Data
public class TaskResponse {
    private UUID taskId;
    private String taskName;
    private String taskDetail;
    private TaskStatus taskStat;
    private UUID taskProjectId;
}
