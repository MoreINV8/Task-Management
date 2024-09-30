package ku.cs.task_management.exceptions;

import java.util.UUID;

public class NotFoundTaskException extends Exception {
    public NotFoundTaskException(UUID taskId) {
        super("Task with id " + taskId + " not found");
    }
}
