package ku.cs.task_management.exceptions;

import java.util.UUID;

public class NotFoundMeetingException extends Exception {
    public NotFoundMeetingException(UUID meetingId) {
        super("Meeting with ID: " + meetingId + " not found");
    }
}
