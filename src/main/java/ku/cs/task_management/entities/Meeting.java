package ku.cs.task_management.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "meeting")
public class Meeting {
    @Id
    @GeneratedValue
    @Column(name = "meeting_id")
    private UUID meetingId;
}
