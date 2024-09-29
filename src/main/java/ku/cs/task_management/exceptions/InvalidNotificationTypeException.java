package ku.cs.task_management.exceptions;

public class InvalidNotificationTypeException extends Exception{
    public InvalidNotificationTypeException() {
        super("receive invalid type of the notification");
    }
}
