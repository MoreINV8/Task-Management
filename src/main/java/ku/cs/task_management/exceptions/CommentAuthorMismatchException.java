package ku.cs.task_management.exceptions;

import ku.cs.task_management.entities.Member;

import java.util.UUID;

public class CommentAuthorMismatchException extends Throwable {
    public CommentAuthorMismatchException(Member commentAuthor, UUID commentMemberId) {
        super(commentMemberId  + " is not the author of this comment member: " + commentAuthor);
    }
}
