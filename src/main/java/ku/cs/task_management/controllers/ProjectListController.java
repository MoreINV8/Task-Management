package ku.cs.task_management.controllers;

import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.responses.ProjectListResponse;
import ku.cs.task_management.services.AssignmentService;
import ku.cs.task_management.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ProjectListController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private AssignmentService assignmentService;

    @GetMapping("/project-list")
    public ResponseEntity<ProjectListResponse> projectList(@RequestParam UUID m)
            throws NotFoundMemberException {
        return ResponseEntity.ok(
                new ProjectListResponse(
                        projectService.getAllProjectsByOwnerId(m),
                        assignmentService.getAllProjectByMemberId(m
                        )
                )
        );
    }
}
