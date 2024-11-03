package ku.cs.task_management.controllers;

import ku.cs.task_management.exceptions.*;
import ku.cs.task_management.requests.notification_requests.NotificationSendRequest;
import ku.cs.task_management.responses.NotificationResponse;
import ku.cs.task_management.responses.SuccessResponse;
import ku.cs.task_management.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/notification")
    public ResponseEntity<NotificationResponse> sendNotification(@RequestBody NotificationSendRequest request)
            throws InvalidRequestException, NotFoundMemberException, NotFoundTaskException, NotFoundProjectException, NotFoundMeetingException {
        return new ResponseEntity<>(notificationService.insertNotification(request), HttpStatus.CREATED);
    }

    @GetMapping("/notification")
    public ResponseEntity<List<NotificationResponse>> getNotificationsOfMember(@RequestParam UUID m)
            throws NotFoundMemberException, IllegalArgumentException {
        return ResponseEntity.ok(notificationService.getNotificationFromMemberId(m));
    }

    @PutMapping("/notification")
    public ResponseEntity<NotificationResponse> readNotification(@RequestParam UUID n)
            throws NotFoundNotificationException, IllegalArgumentException {
        return new ResponseEntity<>(notificationService.readNotification(n), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/notification")
    public ResponseEntity<SuccessResponse> deleteNotification(@RequestParam UUID n)
            throws NotFoundNotificationException, IllegalArgumentException {
        return new ResponseEntity<>(notificationService.deleteNotification(n), HttpStatus.ACCEPTED);
    }
}
