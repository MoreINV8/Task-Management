package ku.cs.task_management.responses;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ExceptionResponse {
    private String error;
    private HttpStatus status;

    public ExceptionResponse(String error, HttpStatus status) {
        this.error = error;
        this.status = status;
    }
}
