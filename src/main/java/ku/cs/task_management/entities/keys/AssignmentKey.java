package ku.cs.task_management.entities.keys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class AssignmentKey implements Serializable {

    @Column(name = "member_id")
    private UUID memberId;

    @Column(name = "project_id")
    private UUID projectId;
}
