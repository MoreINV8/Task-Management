package ku.cs.task_management.controllers;

import ku.cs.task_management.exceptions.NotFoundProjectException;
import ku.cs.task_management.responses.ProjectBoardResponse;
import ku.cs.task_management.services.ProjectBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ProjectBoardController {

    @Autowired
    private  ProjectBoardService projectBoardService;

    @GetMapping("/project-board")
    public ResponseEntity<ProjectBoardResponse> getProjectBoard(@RequestParam UUID i) throws NotFoundProjectException {
        return new ResponseEntity<>(projectBoardService.getProjectBoard(i), HttpStatus.OK);
    }
}
