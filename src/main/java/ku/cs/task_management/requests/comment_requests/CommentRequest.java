package ku.cs.task_management.requests.comment_requests;

import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class CommentRequest {
    private UUID commentId;
    private Date commentPostTime;
    private String commentContent;
    private UUID commentTaskId;
    private UUID commentMemberId;
}
