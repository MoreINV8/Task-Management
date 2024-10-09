package ku.cs.task_management.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "meeting")
public class Meeting {
    @Id
    @GeneratedValue
    @Column(name = "meeting_id")
    private UUID meetingId;

    @Column(name = "meeting_topic")
    private String meetingTopic;

    @Column(name = "meeting_date")
    private Date meetingDate;

    @Column(name = "meeting_location")
    private String meetingLocation;

    @ManyToOne
    @JoinColumn(name = "project_fk", referencedColumnName = "project_id")
    private Project meetingProject;

    @OneToMany(mappedBy = "notificationMeeting", cascade = CascadeType.ALL)
    private List<Notification> meetingNotifications;
}
