package ku.cs.task_management.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
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

    @ManyToOne
    @JoinColumn(name = "member_fk", referencedColumnName = "member_id")
    private Member projectOwner;
}
