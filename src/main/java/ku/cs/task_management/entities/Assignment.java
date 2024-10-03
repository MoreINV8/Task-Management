package ku.cs.task_management.entities;

import jakarta.persistence.*;
import ku.cs.task_management.entities.keys.AssignmentKey;
import lombok.Data;

@Data
@Entity
public class Assignment {

    @EmbeddedId
    private AssignmentKey id;

    private String role;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "project_Id")
    private Project project;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private Member member;
}
