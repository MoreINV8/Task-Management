package ku.cs.task_management.entities;

import jakarta.persistence.*;
import ku.cs.task_management.entities.keys.AttendanceKey;
import lombok.Data;

@Data
@Entity
public class Attendance {

    @EmbeddedId
    private AttendanceKey id;

    @ManyToOne
    @MapsId("meetingId")
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private Member member;
}
