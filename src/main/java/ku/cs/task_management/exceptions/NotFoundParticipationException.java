package ku.cs.task_management.exceptions;

import java.util.UUID;

public class NotFoundParticipationException extends Exception {
    public NotFoundParticipationException(UUID memberId, UUID taskId){
        super("Participation not found for " + memberId + " for " + taskId);
    }
}
