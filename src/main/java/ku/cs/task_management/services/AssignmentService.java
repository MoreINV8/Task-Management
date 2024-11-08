package ku.cs.task_management.services;

import ku.cs.task_management.entities.Assignment;
import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.Notification;
import ku.cs.task_management.entities.Project;
import ku.cs.task_management.entities.keys.AssignmentKey;
import ku.cs.task_management.exceptions.NotFoundAssignmentException;
import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.exceptions.NotFoundNotificationException;
import ku.cs.task_management.exceptions.NotFoundProjectException;
import ku.cs.task_management.repositories.AssignmentRepository;
import ku.cs.task_management.repositories.MemberRepository;
import ku.cs.task_management.repositories.NotificationRepository;
import ku.cs.task_management.repositories.ProjectRepository;
import ku.cs.task_management.requests.assignment_requests.AssignRequest;
import ku.cs.task_management.requests.assignment_requests.KickRequest;
import ku.cs.task_management.requests.log_request.LogRequest;
import ku.cs.task_management.responses.AssignResponse;
import ku.cs.task_management.responses.MemberResponse;
import ku.cs.task_management.responses.ProjectResponse;
import ku.cs.task_management.responses.SuccessResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AssignmentService {
    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private LogService logService;

    // TODO: return in List of Object or just return in List of UUID, which one is better
    public List<AssignResponse> getAllMembersByProjectId(UUID projectId)
            throws NotFoundProjectException {

        projectRepository.findById(projectId).orElseThrow(() -> new NotFoundProjectException(projectId));
        List<AssignResponse> responses = new ArrayList<>();
        for (Assignment assignment : assignmentRepository.findAllByProjectProjectId(projectId)) {
            AssignResponse response = new AssignResponse(assignment);
            responses.add(response);
        }
        return responses;
    }

    public List<ProjectResponse> getAllProjectByMemberId(UUID memberId) {
        List<ProjectResponse> responses = new ArrayList<>();
        for (Assignment assignment:assignmentRepository.findAllByMemberMemberId(memberId)) {
            ProjectResponse projectResponse = new ProjectResponse(assignment.getProject());
            projectResponse.setProjectOwner(modelMapper.map(assignment.getProject().getProjectOwner(), MemberResponse.class));

            responses.add(projectResponse);
        }
        return responses;
    }

    public SuccessResponse assign(UUID projectId, AssignRequest request)
            throws NotFoundProjectException, NotFoundMemberException {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundProjectException(projectId));

        Member member = memberRepository.findMemberByEmail(request.getEmail());

        if(member == null) {
            throw new NotFoundMemberException(request.getEmail());
        }

        // add log
        LogRequest logRequest = new LogRequest(member.getMemberId() + " have been added to project " + project.getProjectName(), project.getProjectId(), project.getProjectOwner().getMemberId());
        logService.saveLog(logRequest);

        Assignment assignment = new Assignment();
        assignment.setId(new AssignmentKey(member.getMemberId(), projectId));
        assignment.setRole(request.getRole());
        assignment.setMember(member);
        assignment.setProject(project);

        assignmentRepository.save(assignment);
        return new SuccessResponse(member.getMemberId() + " was added into " + projectId, HttpStatus.OK);
    }

    public SuccessResponse unassign(UUID projectId, KickRequest request)
            throws NotFoundProjectException, NotFoundMemberException, NotFoundAssignmentException, NotFoundNotificationException {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundProjectException(projectId));

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundMemberException(request.getMemberId()));

        // add log
        LogRequest logRequest = new LogRequest(member.getDetail().getMemberName() + " have been kicked off project " + project.getProjectName(), project.getProjectId(), project.getProjectOwner().getMemberId());
        logService.saveLog(logRequest);

        // remove notification from assignee
        List<Notification> notifications = notificationRepository.getNotificationsByReceiverId(member.getMemberId());
        if (notifications != null) {
            for (Notification n : notifications) {
                notificationService.deleteNotification(n.getNotificationId());
            }
        }

        AssignmentKey assignmentKey = new AssignmentKey(request.getMemberId(), projectId);
        Assignment assignment = assignmentRepository
                .findById(assignmentKey)
                .orElseThrow(() -> new NotFoundAssignmentException(request.getMemberId(), projectId));
        assignmentRepository.delete(assignment);

        project.getProjectMembers().remove(assignment);
        projectRepository.save(project);

        return new SuccessResponse(request.getMemberId() + " was removed from " + projectId, HttpStatus.OK);
    }

}
