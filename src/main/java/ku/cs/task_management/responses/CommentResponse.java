package ku.cs.task_management.responses;

import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class CommentResponse {
    private UUID commentId;
    private Date commentPostTime;
    private String commentContent;
    private UUID commentTaskId;
    private UUID commentMemberId;
}
