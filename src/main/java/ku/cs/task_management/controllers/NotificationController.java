package ku.cs.task_management.controllers;

import ku.cs.task_management.exceptions.InvalidNotificationTypeException;
import ku.cs.task_management.exceptions.InvalidRequestException;
import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.requests.notification_requests.NotificationSendRequest;
import ku.cs.task_management.responses.NotificationResponse;
import ku.cs.task_management.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/notification")
    public ResponseEntity<NotificationResponse> sendNotification(@RequestBody NotificationSendRequest request)
            throws InvalidRequestException, InvalidNotificationTypeException, NotFoundMemberException {
        return new ResponseEntity<>(notificationService.insertNotification(request), HttpStatus.CREATED);
    }
}
