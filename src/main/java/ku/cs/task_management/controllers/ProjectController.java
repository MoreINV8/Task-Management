package ku.cs.task_management.controllers;

import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.Project;
import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.repositories.MemberRepository;
import ku.cs.task_management.requests.project_requests.ProjectCreateRequest;
import ku.cs.task_management.responses.ExceptionResponse;
import ku.cs.task_management.responses.ProjectResponse;
import ku.cs.task_management.services.ProjectService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    public ResponseEntity<List<ProjectResponse>> getAllMyProject(@PathVariable String email) {
        Member member = memberRepository.findMemberByEmail(email);
        if (member == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<ProjectResponse> projects = projectService.getAllProjectsByOwnerId(member.getMemberId());
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping("/project/create")
    public ResponseEntity<ProjectResponse> createProject(@RequestBody ProjectCreateRequest projectCreateRequest) throws NotFoundMemberException {
        return new ResponseEntity<>(projectService.createProject(projectCreateRequest), HttpStatus.CREATED);
    }
}
