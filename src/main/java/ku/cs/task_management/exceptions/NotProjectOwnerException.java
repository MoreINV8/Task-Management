package ku.cs.task_management.exceptions;

import java.util.UUID;

public class NotProjectOwnerException extends Exception{
    public NotProjectOwnerException(UUID memberId, UUID projectOwnerId) {
        super("Member with ID: " + memberId + " is not the owner of the project. Expected owner ID: " + projectOwnerId);}
}
