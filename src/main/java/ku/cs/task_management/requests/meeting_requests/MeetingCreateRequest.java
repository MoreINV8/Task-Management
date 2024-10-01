package ku.cs.task_management.requests.meeting_requests;

import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class MeetingCreateRequest {
    private String meetingTopic;
    private Date meetingDate;
    private String meetingLocation;
    private UUID meetingProjectId;
}
