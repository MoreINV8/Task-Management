package ku.cs.task_management.responses;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ExceptionResponse {
    private HttpStatus status;
    private String error;

    public ExceptionResponse(String error, HttpStatus status) {
        this.error = error;
        this.status = status;
    }
}
