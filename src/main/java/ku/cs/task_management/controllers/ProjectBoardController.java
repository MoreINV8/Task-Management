package ku.cs.task_management.controllers;

import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.exceptions.NotFoundProjectException;
import ku.cs.task_management.responses.ProjectBoardResponse;
import ku.cs.task_management.services.ProjectBoardService;
import ku.cs.task_management.services.RecentlyService;
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

    @Autowired
    private RecentlyService recentlyService;

    @GetMapping("/project-board")
    public ResponseEntity<ProjectBoardResponse> getProjectBoard(@RequestParam UUID i, @RequestParam UUID m) throws NotFoundProjectException, NotFoundMemberException {
        recentlyService.saveRecently(m, i);
        return new ResponseEntity<>(projectBoardService.getProjectBoard(i), HttpStatus.OK);
    }
}
