package ku.cs.task_management.controllers;

import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.exceptions.NotFoundProjectException;
import ku.cs.task_management.requests.log_request.LogRequest;
import ku.cs.task_management.responses.LogResponse;
import ku.cs.task_management.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping("/log")
    public ResponseEntity<LogResponse> saveLog(@RequestBody LogRequest request)
            throws NotFoundProjectException, NotFoundMemberException {
        return ResponseEntity.ok(logService.saveLog(request));
    }

    @GetMapping("/log")
    public ResponseEntity<List<LogResponse>> getLog(@RequestParam UUID p)
            throws NotFoundProjectException {
        return ResponseEntity.ok(logService.getLogs(p));
    }
}
