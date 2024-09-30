package ku.cs.task_management.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Log {
    @Id
    @GeneratedValue
    @Column(name = "log_id")
    private int logId;

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
