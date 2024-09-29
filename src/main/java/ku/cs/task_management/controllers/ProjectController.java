package ku.cs.task_management.controllers;

import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.Project;
import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.exceptions.NotFoundProjectException;
import ku.cs.task_management.repositories.MemberRepository;
import ku.cs.task_management.requests.project_requests.ProjectRequest;
import ku.cs.task_management.responses.ProjectResponse;
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

    @GetMapping("/project/")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/project/{email}")
    public ResponseEntity<List<ProjectResponse>> getAllMyProject(@PathVariable String email) throws NotFoundMemberException {
        Member member = memberRepository.findMemberByEmail(email);
        if (member == null) {
            throw new NotFoundMemberException(email);
        }
        List<ProjectResponse> projects = projectService.getAllProjectsByOwnerId(member.getMemberId());
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping("/project/create")
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectRequest projectCreateRequest) throws NotFoundMemberException {
        return new ResponseEntity<>(projectService.createProject(projectCreateRequest), HttpStatus.CREATED);
    }


    @PutMapping("/project/{email}/{projectId}/edit")
    public ResponseEntity<ProjectResponse> editProjectDetail(@PathVariable String email,
                                                             @PathVariable UUID projectId,
                                                             @RequestBody ProjectRequest projectEditRequest)
            throws NotFoundMemberException, NotFoundProjectException {
        Member member = memberRepository.findMemberByEmail(email);
        if (member == null) {
            throw new NotFoundMemberException(email);
        }

        return new ResponseEntity<>(projectService.updateProject(projectId, projectEditRequest, member.getMemberId()), HttpStatus.OK);
    }

}
