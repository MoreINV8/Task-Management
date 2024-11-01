package ku.cs.task_management.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "recently_view")
public class RecentlyView {

    @Id
    @GeneratedValue
    @Column(name = "recently_id")
    private UUID recentlyId;

    @Column(name = "recently_time")
    private LocalDateTime recentlyTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id_fk")
    private Project recentlyProject;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id_fk")
    private Member recentViewer;
}
