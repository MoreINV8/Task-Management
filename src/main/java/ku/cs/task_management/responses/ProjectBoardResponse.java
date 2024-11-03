package ku.cs.task_management.responses;

import ku.cs.task_management.entities.Project;
import lombok.Data;

import java.util.List;

@Data
public class ProjectBoardResponse {
    private ProjectResponse project;
    private List<TaskResponse> tasks;
    private List<AssignResponse> assigns;
    private List<MeetingResponse> meetings;
    private List<LogResponse> logs;
}
