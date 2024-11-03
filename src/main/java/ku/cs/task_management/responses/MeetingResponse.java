package ku.cs.task_management.responses;

import ku.cs.task_management.entities.Meeting;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class MeetingResponse {
    private UUID meetingId;
    private String meetingTopic;
    private Date meetingDate;
    private String meetingLocation;
    private UUID meetingProjectId;

    public MeetingResponse(Meeting meeting) {
        this.meetingId = meeting.getMeetingId();
        this.meetingTopic = meeting.getMeetingTopic();
        this.meetingDate = meeting.getMeetingDate();
        this.meetingLocation = meeting.getMeetingLocation();
        this.meetingProjectId = meeting.getMeetingProject().getProjectId();
    }
}
