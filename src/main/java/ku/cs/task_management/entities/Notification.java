package ku.cs.task_management.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
@Entity
public class Notification {

    @Id
    @GeneratedValue
    @Column(name = "notification_id")
    private UUID notificationId;

    @Column(name = "notification_time")
    private Date notificationTime;

    @Column(name = "notification_detail")
    private String notificationDetail;

    @Column(name = "notification_status")
    private int notificationStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id_fk")
    private Project notificationProject;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id_fk")
    private Task notificationTask;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "meeting_id_fk")
    private Meeting notificationMeeting;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id_fk")
    private Member notificationReceiver;
}
