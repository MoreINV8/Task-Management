package ku.cs.task_management.exceptions;

public class NotFoundMemberException extends Exception{
    public NotFoundMemberException(String email) {
        super("not found member with email '" + email + "'");
    }
}
