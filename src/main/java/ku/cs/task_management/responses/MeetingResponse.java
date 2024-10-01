package ku.cs.task_management.responses;

import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class MeetingResponse {
    private UUID meetingId;
    private String meetingTopic;
    private Date meetingDate;
    private String meetingLocation;
    private UUID meetingProject;
}
