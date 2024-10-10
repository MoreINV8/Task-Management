package ku.cs.task_management.controllers;

import ku.cs.task_management.entities.Project;
import ku.cs.task_management.exceptions.*;
import ku.cs.task_management.repositories.MemberRepository;
import ku.cs.task_management.requests.assignment_requests.AssignRequest;
import ku.cs.task_management.requests.assignment_requests.KickRequest;
import ku.cs.task_management.requests.project_requests.ProjectFavourRequest;
import ku.cs.task_management.requests.project_requests.ProjectRequest;
import ku.cs.task_management.responses.AssignResponse;
import ku.cs.task_management.responses.ProjectResponse;
import ku.cs.task_management.responses.SuccessResponse;
import ku.cs.task_management.services.AssignmentService;
import ku.cs.task_management.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AssignmentService assignmentService;

    @GetMapping("/project/")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/project/{email}")
    public ResponseEntity<List<ProjectResponse>> getAllMyProject(@PathVariable String email) throws NotFoundMemberException {
        return new ResponseEntity<>(projectService.getAllProjectsByOwnerEmail(email), HttpStatus.OK);
    }

    @GetMapping("/project/{email}/{projectId}")
    public ResponseEntity<ProjectResponse> getProjectDetail(@PathVariable String email,
                                                             @PathVariable UUID projectId)
        throws NotFoundMemberException, NotFoundProjectException, NotProjectOwnerException {
        return new ResponseEntity<>(projectService.getProjectDetail(email, projectId), HttpStatus.OK);
    }

    @PostMapping("/project/create")
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest projectCreateRequest) throws NotFoundMemberException {
        return new ResponseEntity<>(projectService.createProject(projectCreateRequest), HttpStatus.CREATED);
    }

    // assignment GET
    @GetMapping("/{projectId}/assignment")
    public ResponseEntity<List<AssignResponse>> getAllMembersByProjectId(@PathVariable UUID projectId) throws NotFoundProjectException {
        return new ResponseEntity<>(assignmentService.getAllMembersByProjectId(projectId), HttpStatus.OK);
    }

    // assignment POST
    @PostMapping("/{projectId}/assign")
    public ResponseEntity<SuccessResponse> assignMember(@PathVariable UUID projectId, @RequestBody AssignRequest request) throws NotFoundProjectException, NotFoundMemberException {
        return new ResponseEntity<>(assignmentService.assign(projectId, request), HttpStatus.OK);
    }

    // assignment DELETE
    @DeleteMapping("/{projectId}/unassign")
    public ResponseEntity<SuccessResponse> unassignMember(@PathVariable UUID projectId, @RequestBody KickRequest request) throws NotFoundProjectException, NotFoundMemberException, NotFoundAssignmentException {
        return new ResponseEntity<>(assignmentService.unassign(projectId, request), HttpStatus.OK);
    }

    @PutMapping("/project/update-favour")
    public ResponseEntity<SuccessResponse> updateFavour(@RequestBody ProjectFavourRequest request) throws NotFoundProjectException, InvalidRequestException {
        return new ResponseEntity<>(projectService.updateProjectFavour(request), HttpStatus.OK);
    }
    @PutMapping("/project/{email}/{projectId}/edit")
    public ResponseEntity<ProjectResponse> editProjectDetail(@PathVariable String email,
                                                             @PathVariable UUID projectId,
                                                             @RequestBody ProjectRequest projectEditRequest)
            throws NotFoundMemberException, NotFoundProjectException, NotProjectOwnerException {
        return new ResponseEntity<>(projectService.updateProject(projectId, projectEditRequest, email), HttpStatus.OK);
    }
}
