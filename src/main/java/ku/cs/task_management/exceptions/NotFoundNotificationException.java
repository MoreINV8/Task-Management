package ku.cs.task_management.exceptions;

public class NotFoundNotificationException extends Exception {
    public NotFoundNotificationException(String notificationId) {
        super("could not find notification with id '" + notificationId + "'");
    }
}
