package ku.cs.task_management.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue
    @Column(name = "task_id")
    private UUID taskId;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_detail")
    private String taskDetail;

    @Column(name = "task_status")
    private int taskStatus;

    @ManyToOne
    @JoinColumn(name = "project_fk", referencedColumnName = "project_id")
    private Project taskProject;

    @OneToMany(mappedBy = "commentTask", cascade = CascadeType.ALL)
    private List<Comment> taskComments;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Notification> taskNotifications;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private List<Log> taskLogs;
}
