package ku.cs.task_management.exceptions;

import java.util.UUID;

public class NotFoundAssignmentException extends Exception {
    public NotFoundAssignmentException(UUID memberId, UUID projectId) {
        super("Assignment not found for " + memberId + " for " + projectId);
    }
}
