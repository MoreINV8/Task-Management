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
        return new ResponseEntity<>(taskService.getAllTasksByProjectId(projectId), HttpStatus.ACCEPTED);
    }
    @PostMapping("/{projectId}/task/create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskCreateRequest request)
        throws NotFoundProjectException {
        return new ResponseEntity<>(taskService.createTask(request), HttpStatus.CREATED);
    }

    @PutMapping("/{projectId}/task/edit")
    public ResponseEntity<TaskResponse> updateDetailTask(@RequestBody TaskUpdateRequest request)
            throws NotFoundProjectException, NotFoundTaskException {
        return new ResponseEntity<>(taskService.updateDetailTask(request), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{projectId}/{taskId}/delete")
    public ResponseEntity<TaskResponse> deleteTask(@PathVariable UUID projectId, @PathVariable UUID taskId)
            throws NotFoundProjectException, NotFoundTaskException {
        return new ResponseEntity<>(taskService.deleteTask(projectId, taskId), HttpStatus.ACCEPTED);
    }
}
