package ku.cs.task_management.utils;

import ku.cs.task_management.entities.Notification;
import ku.cs.task_management.exceptions.NotFoundMeetingException;
import ku.cs.task_management.exceptions.NotFoundProjectException;
import ku.cs.task_management.exceptions.NotFoundTaskException;

import java.util.UUID;

public interface NotificationHandler {
    void handle(Notification notification, UUID objectId) throws NotFoundProjectException, NotFoundTaskException, NotFoundMeetingException;
}
