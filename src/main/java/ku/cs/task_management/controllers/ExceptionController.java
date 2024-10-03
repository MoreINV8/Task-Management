package ku.cs.task_management.controllers;

import ku.cs.task_management.exceptions.*;
import ku.cs.task_management.responses.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UnavailableEmailException.class)
    public ResponseEntity<ExceptionResponse> unavailableEmailExceptionHandler(UnavailableEmailException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getMessage();

        return new ResponseEntity<>(new ExceptionResponse(message, status), status);
    }

    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<ExceptionResponse> notFoundMemberExceptionHandler(NotFoundMemberException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getMessage();

        return new ResponseEntity<>(new ExceptionResponse(message, status), status);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ExceptionResponse> wrongPasswordExceptionHandler(WrongPasswordException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getMessage();

        return new ResponseEntity<>(new ExceptionResponse(message, status), status);
    }

    @ExceptionHandler(SamePasswordException.class)
    public ResponseEntity<ExceptionResponse> samePasswordExceptionHandler(SamePasswordException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getMessage();

        return new ResponseEntity<>(new ExceptionResponse(message, status), status);
    }

    @ExceptionHandler(InvalidNotificationTypeException.class)
    public ResponseEntity<ExceptionResponse> invalidNotificationTypeExceptionHandler(InvalidNotificationTypeException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getMessage();

        return new ResponseEntity<>(new ExceptionResponse(message, status), status);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ExceptionResponse> invalidRequestExceptionHandler(InvalidRequestException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getMessage();

        return new ResponseEntity<>(new ExceptionResponse(message, status), status);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getMessage();

        return new ResponseEntity<>(new ExceptionResponse(message, status), status);
    }

    @ExceptionHandler(NotFoundNotificationException.class)
    public ResponseEntity<ExceptionResponse> notFoundNotificationException(NotFoundNotificationException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getMessage();

        return new ResponseEntity<>(new ExceptionResponse(message, status), status);
    }

    @ExceptionHandler(NotProjectOwnerException.class)
    public ResponseEntity<ExceptionResponse> notProjectOwnerExceptionHandler(NotProjectOwnerException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getMessage();

        return new ResponseEntity<>(new ExceptionResponse(message, status), status);
    }

    @ExceptionHandler(NotFoundProjectException.class)
    public ResponseEntity<ExceptionResponse> notFoundProjectException(NotFoundProjectException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getMessage();

        return new ResponseEntity<>(new ExceptionResponse(message, status), status);
    }

    @ExceptionHandler(NotFoundMeetingException.class)
    public ResponseEntity<ExceptionResponse> notFoundMeetingException(NotFoundMeetingException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getMessage();

        return new ResponseEntity<>(new ExceptionResponse(message, status), status);
    }

    @ExceptionHandler(NotFoundCommentException.class)
    public ResponseEntity<ExceptionResponse> notFoundCommentException(NotFoundCommentException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getMessage();

        return new ResponseEntity<>(new ExceptionResponse(message, status), status);
    }

    @ExceptionHandler(CommentAuthorMismatchException.class)
    public ResponseEntity<ExceptionResponse> commentAuthorMismatchException(CommentAuthorMismatchException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getMessage();

        return new ResponseEntity<>(new ExceptionResponse(message, status), status);
    }

    @ExceptionHandler(NotFoundTaskException.class)
    public ResponseEntity<ExceptionResponse> notFoundTaskException(NotFoundTaskException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getMessage();

        return new ResponseEntity<>(new ExceptionResponse(message, status), status);
    }

    @ExceptionHandler(NotFoundAssignmentException.class)
    public ResponseEntity<ExceptionResponse> notFoundAssignmentException(NotFoundAssignmentException e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = e.getMessage();

        return new ResponseEntity<>(new ExceptionResponse(message, status), status);
    }
}
