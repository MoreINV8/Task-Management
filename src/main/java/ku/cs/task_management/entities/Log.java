package ku.cs.task_management.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@Entity
public class Log {
    @Id
    @GeneratedValue
    @Column(name = "log_id")
    private int logId;

    @Column(name = "log_action")
    private String action;

    @Column(name = "log_time")
    private LocalDateTime time;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id_fk")
    private Project project;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id_fk")
    private Member actor;
}
