package ku.cs.task_management.exceptions;

public class NotFoundProjectException extends Exception {
    public NotFoundProjectException() {
        super("Project not found");
    }
}
