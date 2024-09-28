package ku.cs.task_management.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
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
    private short notificationStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id_fk")
    private Project project;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id_fk")
    private Task task;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "meeting_id_fk")
    private Meeting meeting;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id_fk")
    private Member receiver;
}
