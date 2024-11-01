package ku.cs.task_management.responses;

import ku.cs.task_management.commons.TaskStatus;
import ku.cs.task_management.entities.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class TaskResponse {
    private UUID taskId;
    private String taskName;
    private String taskDetail;
    private TaskStatus taskStatus;
    private UUID taskProjectId;
    private List<CommentResponse> taskComments;
    private List<Member> taskParticipants;

    public TaskResponse(Task t) {
        this.taskId = t.getTaskId();
        this.taskName = t.getTaskName();
        this.taskDetail = t.getTaskDetail();
        this.taskStatus = t.getTaskStatus();
        this.taskProjectId = t.getTaskProject().getProjectId();
        this.taskComments = new ArrayList<>();
        for (Comment comment : t.getTaskComments()) {
            CommentResponse response = new CommentResponse();
            response.setCommentId(comment.getCommentId());
            response.setCommentTaskId(comment.getCommentTask().getTaskId());
            response.setCommentContent(comment.getCommentContent());
            response.setCommentPostTime(comment.getCommentPostTime());
            response.setCommentMemberId(comment.getCommentAuthor().getMemberId());
            this.taskComments.add(response);
        }
    }
}
