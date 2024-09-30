package ku.cs.task_management.controllers;

import ku.cs.task_management.requests.task_requests.TaskRequest;
import ku.cs.task_management.responses.TaskResponse;
import ku.cs.task_management.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/task")
    public List<TaskResponse> getTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/task/create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskRequest request) {
        return new ResponseEntity<>(taskService.createTask(request), HttpStatus.CREATED);
    }
}
