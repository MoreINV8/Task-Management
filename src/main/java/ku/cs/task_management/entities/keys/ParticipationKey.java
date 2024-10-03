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
public class ParticipationKey implements Serializable {

    @Column(name = "task_id")
    private UUID taskId;

    @Column(name = "member_id")
    private UUID memberId;
}
