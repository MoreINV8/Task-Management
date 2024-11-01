package ku.cs.task_management.responses;

import ku.cs.task_management.commons.TaskStatus;
import ku.cs.task_management.entities.Task;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class KanBanResponse {
    private UUID taskId;
    private String taskName;
    private String projectName;
    private TaskStatus status;

    public KanBanResponse(Task t) {
        this.taskId = t.getTaskId();
        this.taskName = t.getTaskName();
        this.projectName = t.getTaskProject().getProjectName();
        this.status = t.getTaskStatus();
    }
}
