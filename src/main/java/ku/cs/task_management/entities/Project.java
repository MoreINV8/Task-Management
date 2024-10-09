package ku.cs.task_management.entities;

import jakarta.persistence.*;
import ku.cs.task_management.commons.ProjectStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue
    @Column(name = "project_id")
    private UUID projectId;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "project_description")
    private String projectDescription;

    @Column(name = "project_deadline")
    private Date projectDeadline;

    @Column(name = "project_fav")
    private ProjectStatus projectFav;

    @ManyToOne
    @JoinColumn(name = "member_fk", referencedColumnName = "member_id")
    private Member projectOwner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Assignment> projectMembers;

    @OneToMany(mappedBy = "taskProject", cascade = CascadeType.ALL)
    private List<Task> projectTasks;

    @OneToMany(mappedBy = "meetingProject", cascade = CascadeType.ALL)
    private List<Meeting> projectMeetings;

    @OneToMany(mappedBy = "notificationProject", cascade = CascadeType.ALL)
    private List<Notification> projectNotifications;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Log> projectLogs;
}
