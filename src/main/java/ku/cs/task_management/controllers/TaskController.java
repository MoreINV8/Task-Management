package ku.cs.task_management.controllers;

import ku.cs.task_management.commons.TaskStatus;
import ku.cs.task_management.exceptions.InvalidRequestException;
import ku.cs.task_management.exceptions.NotFoundProjectException;
import ku.cs.task_management.exceptions.NotFoundTaskException;
import ku.cs.task_management.requests.task_requests.TaskCreateRequest;
import ku.cs.task_management.requests.task_requests.TaskUpdateRequest;
import ku.cs.task_management.responses.TaskResponse;
import ku.cs.task_management.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/task")
    public List<TaskResponse> getTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/all-task")
    public ResponseEntity<List<TaskResponse>> getAllTasksByProjectId(@RequestParam UUID p) throws NotFoundProjectException {
        return new ResponseEntity<>(taskService.getAllTasksByProjectId(p), HttpStatus.OK);
    }

    @GetMapping("/task/detail")
    public ResponseEntity<TaskResponse> getTaskDetail(@RequestParam UUID p, @RequestParam UUID t)
            throws NotFoundProjectException, NotFoundTaskException {
        return new ResponseEntity<>(taskService.getTaskDetail(p, t), HttpStatus.OK);
    }
    @PostMapping("/task/create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskCreateRequest request)
        throws NotFoundProjectException {
        return new ResponseEntity<>(taskService.createTask(request), HttpStatus.CREATED);
    }

    @PutMapping("/task/update")
    public ResponseEntity<TaskResponse> updateTaskDetail(@RequestBody TaskUpdateRequest request)
            throws NotFoundProjectException, NotFoundTaskException {
        return new ResponseEntity<>(taskService.updateTaskDetail(request), HttpStatus.OK);
    }

    @PutMapping("/task/update-status")
    public ResponseEntity<TaskResponse> updateTaskStatus(@RequestParam UUID t, @RequestParam TaskStatus s)
            throws NotFoundTaskException, InvalidRequestException {
        return new ResponseEntity<>(taskService.changeTaskStatus(t, s), HttpStatus.OK);
    }

    @DeleteMapping("/task/delete")
    public ResponseEntity<TaskResponse> deleteTask(@RequestParam UUID p, @RequestParam UUID t)
            throws NotFoundProjectException, NotFoundTaskException {
        return new ResponseEntity<>(taskService.deleteTask(p, t), HttpStatus.NO_CONTENT);
    }
}
