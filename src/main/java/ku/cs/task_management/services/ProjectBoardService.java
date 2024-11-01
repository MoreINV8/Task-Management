package ku.cs.task_management.services;

import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.Participation;
import ku.cs.task_management.exceptions.NotFoundProjectException;
import ku.cs.task_management.repositories.ParticipationRepository;
import ku.cs.task_management.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectBoardService {

    @Autowired
    private AssignmentService assignmentService;
    @Autowired
    private ParticipationRepository participationRepository;
    @Autowired
    private TaskService taskService;
    @Autowired
    private MeetingService meetingService;
    @Autowired
    private LogService logService;

    public ProjectBoardResponse getProjectBoard(UUID projectId) throws NotFoundProjectException {
        ProjectBoardResponse projectBoardResponse = new ProjectBoardResponse();

        List<AssignResponse> assignList = assignmentService.getAllMembersByProjectId(projectId);
        List<TaskResponse> taskList = taskService.getAllTasksByProjectId(projectId);
        for (TaskResponse task : taskList) {
            List<Participation> participants = participationRepository.findAllByTaskTaskId(task.getTaskId());
            List<Member> members = participants.stream()
                    .map(Participation::getMember)
                    .collect(Collectors.toList());
            task.setTaskParticipants(members);
        }
        List<MeetingResponse> meetingList = meetingService.getAllMeetingByProject(projectId);
        List<LogResponse> logList = logService.getLogs(projectId);

        projectBoardResponse.setAssigns(assignList);
        projectBoardResponse.setTasks(taskList);
        projectBoardResponse.setMeetings(meetingList);
        projectBoardResponse.setLogs(logList);
        return projectBoardResponse;
    }

}
