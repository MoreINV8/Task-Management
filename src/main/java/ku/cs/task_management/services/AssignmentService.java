package ku.cs.task_management.services;

import ku.cs.task_management.entities.Assignment;
import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.Project;
import ku.cs.task_management.entities.keys.AssignmentKey;
import ku.cs.task_management.exceptions.NotFoundAssignmentException;
import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.exceptions.NotFoundProjectException;
import ku.cs.task_management.repositories.AssignmentRepository;
import ku.cs.task_management.repositories.MemberRepository;
import ku.cs.task_management.repositories.ProjectRepository;
import ku.cs.task_management.requests.assignment_requests.AssignRequest;
import ku.cs.task_management.responses.AssignResponse;
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

    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public List<AssignResponse> getAllMembersByProjectId(UUID projectId) throws NotFoundProjectException {
        projectRepository.findById(projectId).orElseThrow(() -> new NotFoundProjectException(projectId));
        List<AssignResponse> responses = new ArrayList<>();
        for (Assignment assignment : assignmentRepository.findAllByProjectProjectId(projectId)) {
            AssignResponse response = new AssignResponse();
            response.setMember(assignment.getMember().getMemberId());
            responses.add(response);
        }
        return responses;
    }

    public SuccessResponse assign(UUID projectId, AssignRequest request) throws NotFoundProjectException, NotFoundMemberException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundProjectException(projectId));

        Member member = memberRepository.findMemberByMemberId(request.getMemberId());

        if(member == null) {
            throw new NotFoundMemberException(request.getMemberId().toString());
        }

        Assignment assignment = new Assignment();
        assignment.setId(new AssignmentKey(request.getMemberId(), projectId));
        assignment.setRole(request.getRole());
        assignment.setMember(member);
        assignment.setProject(project);

        assignmentRepository.save(assignment);
        return new SuccessResponse(request.getMemberId() + " was added into " + projectId, HttpStatus.OK);
    }

    public SuccessResponse unassign(UUID projectId, AssignRequest request) throws NotFoundProjectException, NotFoundMemberException, NotFoundAssignmentException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundProjectException(projectId));

        Member member = memberRepository.findMemberByMemberId(request.getMemberId());

        if(member == null) {
            throw new NotFoundMemberException(request.getMemberId().toString());
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