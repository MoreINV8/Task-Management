package ku.cs.task_management.exceptions;

import java.util.UUID;

public class NotFoundMemberException extends Exception{
    public NotFoundMemberException(String email) {
        super("not found member with email '" + email + "'");
    }

    public NotFoundMemberException(UUID id) {
        super("not found member with id'" + id + "'");
    }
}
