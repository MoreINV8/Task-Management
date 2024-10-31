package ku.cs.task_management.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import ku.cs.task_management.entities.keys.ParticipationKey;
import lombok.Data;

@Data
@Entity
public class Participation {
    @JsonIgnore
    @EmbeddedId
    private ParticipationKey id;

    @JsonIgnore
    @ManyToOne
    @MapsId("taskId")
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private Member member;
}
