package ku.cs.task_management.responses;

import lombok.Data;

import java.util.List;

@Data
public class ProjectBoardResponse {
    private List<TaskResponse> tasks;
    private List<AssignResponse> assigns;
    private List<MeetingResponse> meetings;
    private List<LogResponse> logs;
}
