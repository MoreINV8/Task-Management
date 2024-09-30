package ku.cs.task_management.controllers;

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

    @GetMapping("/{projectId}/task")
    public ResponseEntity<List<TaskResponse>> getAllTasksByProjectId(@PathVariable UUID projectId) throws NotFoundProjectException {
        return new ResponseEntity<>(taskService.getAllTasksByProjectId(projectId), HttpStatus.OK);
    }

    @GetMapping("{projectId}/task/{taskId}")
    public ResponseEntity<TaskResponse> getTaskDetail(@PathVariable UUID projectId, @PathVariable UUID taskId)
            throws NotFoundProjectException, NotFoundTaskException {
        return new ResponseEntity<>(taskService.getTaskDetail(projectId, taskId), HttpStatus.OK);
    }
    @PostMapping("/{projectId}/task")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskCreateRequest request)
        throws NotFoundProjectException {
        return new ResponseEntity<>(taskService.createTask(request), HttpStatus.CREATED);
    }

    @PutMapping("/{projectId}/task")
    public ResponseEntity<TaskResponse> updateTaskDetail(@RequestBody TaskUpdateRequest request)
            throws NotFoundProjectException, NotFoundTaskException {
        return new ResponseEntity<>(taskService.updateTaskDetail(request), HttpStatus.OK);
    }

    // TODO: how we get the status to change, check it again later
    @PutMapping("/{projectId}/{taskId}/status")
    public ResponseEntity<TaskResponse> updateTaskStatus(@PathVariable UUID taskId, @RequestParam int status)
            throws NotFoundTaskException {
        return new ResponseEntity<>(taskService.changeTaskStatus(taskId, status), HttpStatus.OK);
    }

    @DeleteMapping("/{projectId}/{taskId}")
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable UUID projectId, @PathVariable UUID taskId)
            throws NotFoundProjectException, NotFoundTaskException {
        return new ResponseEntity<>(taskService.deleteTask(projectId, taskId), HttpStatus.NO_CONTENT);
    }
}
