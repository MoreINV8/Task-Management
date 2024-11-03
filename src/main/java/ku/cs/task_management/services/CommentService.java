package ku.cs.task_management.services;

import ku.cs.task_management.entities.Comment;
import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.Task;
import ku.cs.task_management.exceptions.CommentAuthorMismatchException;
import ku.cs.task_management.exceptions.NotFoundCommentException;
import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.exceptions.NotFoundTaskException;
import ku.cs.task_management.repositories.CommentRepository;
import ku.cs.task_management.repositories.MemberRepository;
import ku.cs.task_management.repositories.TaskRepository;
import ku.cs.task_management.requests.comment_requests.CommentRequest;
import ku.cs.task_management.responses.CommentResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MemberRepository memberRepository;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public List<CommentResponse> getAllCommentByTaskId(UUID taskId)
            throws NotFoundTaskException {

        taskRepository.findById(taskId).orElseThrow(() -> new NotFoundTaskException(taskId));
        List<CommentResponse> responses = new ArrayList<>();
        for (Comment comment : commentRepository.findAllByCommentTaskTaskId(taskId)) {
            responses.add(modelMapper.map(comment, CommentResponse.class));
        }
        return responses;
    }

    public CommentResponse createComment(CommentRequest request)
            throws NotFoundTaskException, NotFoundMemberException {

        Comment comment = new Comment();

        Member member = memberRepository.findById(request.getCommentMemberId())
                .orElseThrow(() -> new NotFoundMemberException(request.getCommentMemberId()));

        // finding if task is exist then set.
        comment.setCommentTask(taskRepository
                .findById(request.getCommentTaskId())
                .orElseThrow(() -> new NotFoundTaskException(request.getCommentTaskId())));

        comment.setCommentAuthor(member);
        comment.setCommentContent(request.getCommentContent());

        // set Date
        LocalDate currentTime = LocalDate.now();
        comment.setCommentPostTime(Date.valueOf(currentTime));

        Comment createdComment = commentRepository.save(comment);
        return modelMapper.map(createdComment, CommentResponse.class);
    }

    public CommentResponse editComment(CommentRequest request)
            throws NotFoundTaskException, NotFoundCommentException, CommentAuthorMismatchException {

        // check if task is exist
        taskRepository
                .findById(request.getCommentTaskId())
                .orElseThrow(() -> new NotFoundTaskException(request.getCommentTaskId()));

        Comment comment = commentRepository
                .findById(request.getCommentId())
                .orElseThrow(() -> new NotFoundCommentException(request.getCommentId()));

        // check if Member is not
        if (!comment.getCommentAuthor().getMemberId().equals(request.getCommentMemberId())) {
            throw new CommentAuthorMismatchException(comment.getCommentAuthor(), request.getCommentMemberId());
        }

        comment.setCommentContent(request.getCommentContent());

        // let's see if it uses time to order the comment
        // .setCommentPostTime(Date.valueOf(LocalDate.now()));

        Comment updatedComment = commentRepository.save(comment);
        return modelMapper.map(updatedComment, CommentResponse.class);
    }

    public CommentResponse deleteComment(CommentRequest request)
            throws NotFoundTaskException, NotFoundCommentException, CommentAuthorMismatchException {

        // check if task is exist
        Task task = taskRepository
                .findById(request.getCommentTaskId())
                .orElseThrow(() -> new NotFoundTaskException(request.getCommentTaskId()));

        Comment comment = commentRepository
                .findById(request.getCommentId())
                .orElseThrow(() -> new NotFoundCommentException(request.getCommentId()));

        // check if Member is not
        if (!comment.getCommentAuthor().getMemberId().equals(request.getCommentMemberId())) {
            throw new CommentAuthorMismatchException(comment.getCommentAuthor(), request.getCommentMemberId());
        }

        task.getTaskComments().remove(comment);
        taskRepository.save(task);

        commentRepository.delete(comment);
        return modelMapper.map(comment, CommentResponse.class);
    }
}
