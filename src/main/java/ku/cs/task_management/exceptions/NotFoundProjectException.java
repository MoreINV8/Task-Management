package ku.cs.task_management.exceptions;

import java.util.UUID;

public class NotFoundProjectException extends Exception {
    public NotFoundProjectException(UUID projectId) {
        super("Project: " + projectId + " not found.");
    }
}
