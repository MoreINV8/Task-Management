package ku.cs.task_management.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private UUID commentId;

    @Column(name = "comment_post_time")
    private Date commentPostTime;

    @Column(name = "comment_content")
    private String commentContent;

    @ManyToOne
    @JoinColumn(name = "task_fk", referencedColumnName = "task_id")
    private Task CommentTask;
}
