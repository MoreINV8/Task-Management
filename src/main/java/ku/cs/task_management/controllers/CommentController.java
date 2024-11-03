package ku.cs.task_management.controllers;

import ku.cs.task_management.entities.Comment;
import ku.cs.task_management.exceptions.CommentAuthorMismatchException;
import ku.cs.task_management.exceptions.NotFoundCommentException;
import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.exceptions.NotFoundTaskException;
import ku.cs.task_management.requests.comment_requests.CommentRequest;
import ku.cs.task_management.responses.CommentResponse;
import ku.cs.task_management.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/comment")
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("{taskId}/comment")
    public ResponseEntity<List<CommentResponse>> getCommentsByTaskId(@PathVariable UUID taskId)
            throws NotFoundTaskException {
        return new ResponseEntity<>(commentService.getAllCommentByTaskId(taskId), HttpStatus.OK);
    }

    @PostMapping("/comment/create")
    public ResponseEntity<CommentResponse> createComment(@RequestBody CommentRequest request)
            throws NotFoundTaskException, NotFoundMemberException {
        return new ResponseEntity<>(commentService.createComment(request), HttpStatus.OK);
    }

    @PutMapping("/comment/edit")
    public ResponseEntity<CommentResponse> editComment(@RequestBody CommentRequest request)
            throws NotFoundTaskException, NotFoundCommentException, CommentAuthorMismatchException {
        return new ResponseEntity<>(commentService.editComment(request), HttpStatus.OK);
    }

    @DeleteMapping("/comment/delete")
    public ResponseEntity<CommentResponse> deleteComment(@RequestBody CommentRequest request)
            throws NotFoundTaskException, NotFoundCommentException, CommentAuthorMismatchException {
        return new ResponseEntity<>(commentService.deleteComment(request), HttpStatus.OK);
    }
}
