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
}
