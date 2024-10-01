package ku.cs.task_management.exceptions;

import java.util.UUID;

public class NotFoundCommentException extends Exception {
    public NotFoundCommentException(UUID commentId) {
        super("Comment with id " + commentId + " not found");
    }
}
