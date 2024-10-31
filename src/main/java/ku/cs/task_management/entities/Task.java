package ku.cs.task_management.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import ku.cs.task_management.commons.TaskStatus;
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
    private TaskStatus taskStatus;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "project_fk", referencedColumnName = "project_id")
    private Project taskProject;

    @OneToMany(mappedBy = "commentTask", cascade = CascadeType.ALL)
    private List<Comment> taskComments;

    @OneToMany(mappedBy = "notificationTask", cascade = CascadeType.ALL)
    private List<Notification> taskNotifications;
}
