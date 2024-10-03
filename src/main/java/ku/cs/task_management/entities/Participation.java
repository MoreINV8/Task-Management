package ku.cs.task_management.entities;

import jakarta.persistence.*;
import ku.cs.task_management.entities.keys.ParticipationKey;
import lombok.Data;

@Data
@Entity
public class Participation {

    @EmbeddedId
    private ParticipationKey id;

    @ManyToOne
    @MapsId("taskId")
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private Member member;
}
