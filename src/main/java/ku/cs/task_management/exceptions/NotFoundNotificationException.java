package ku.cs.task_management.exceptions;

import java.util.UUID;

public class NotFoundNotificationException extends Exception {
    public NotFoundNotificationException(UUID notificationId) {
        super("could not find notification with id '" + notificationId + "'");
    }
}
