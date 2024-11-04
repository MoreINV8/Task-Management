package ku.cs.task_management.controllers;

import ku.cs.task_management.commons.TaskStatus;
import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.Participation;
import ku.cs.task_management.exceptions.*;
import ku.cs.task_management.requests.notification_requests.NotificationSendRequest;
import ku.cs.task_management.requests.task_requests.TaskCreateRequest;
import ku.cs.task_management.requests.task_requests.TaskUpdateRequest;
import ku.cs.task_management.responses.SuccessResponse;
import ku.cs.task_management.responses.TaskResponse;
import ku.cs.task_management.services.NotificationService;
import ku.cs.task_management.services.ParticipateService;
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
    @Autowired
    private ParticipateService participateService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/task")
    public List<TaskResponse> getTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/all-task")
    public ResponseEntity<List<TaskResponse>> getAllTasksByProjectId(@RequestParam UUID p)
            throws NotFoundProjectException {
        return new ResponseEntity<>(taskService.getAllTasksByProjectId(p), HttpStatus.OK);
    }

    @GetMapping("/task/detail")
    public ResponseEntity<TaskResponse> getTaskDetail(@RequestParam UUID p, @RequestParam UUID t)
            throws NotFoundProjectException, NotFoundTaskException {
        return new ResponseEntity<>(taskService.getTaskDetail(p, t), HttpStatus.OK);
    }
    @PostMapping("/task/create")
    public ResponseEntity<TaskResponse> createTask(@RequestBody TaskCreateRequest request)
            throws NotFoundProjectException, NotFoundMemberException {
        return new ResponseEntity<>(taskService.createTask(request), HttpStatus.OK);
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

    // participate GET
    @GetMapping("/task/participation")
    public ResponseEntity<List<Participation>> getTaskParticipate(@RequestParam UUID t)
            throws NotFoundTaskException {
        return new ResponseEntity<>(participateService.getAllParticipationByTaskId(t), HttpStatus.OK);
    }

    // participate POST
    @PostMapping("/task/addParticipate")
    public ResponseEntity<SuccessResponse> addParticipate(@RequestParam UUID t, @RequestBody List<UUID> members)
            throws NotFoundTaskException, NotFoundMemberException, NotFoundProjectException, NotFoundMeetingException, InvalidRequestException {
        // add notification to added task member
        for (UUID memberId : members) {
            notificationService.insertNotification(NotificationSendRequest.task(t), memberId);
        }

        return new ResponseEntity<>(participateService.addParticipation(t, members), HttpStatus.OK);
    }

    // participate DELETE
    @DeleteMapping("/task/deleteParticipate")
    public ResponseEntity<SuccessResponse> deleteParticipate(@RequestParam UUID t, @RequestBody List<UUID> participation)
            throws NotFoundTaskException, NotFoundMemberException, NotFoundParticipationException {
        return new ResponseEntity<>(participateService.removeParticipation(t, participation), HttpStatus.OK);
    }

    @DeleteMapping("/task/delete")
    public ResponseEntity<TaskResponse> deleteTask(@RequestParam UUID t)
            throws NotFoundTaskException, NotFoundCommentException, NotFoundParticipationException, NotFoundNotificationException {
        return new ResponseEntity<>(taskService.deleteTask(t), HttpStatus.OK);
    }
}
