package ku.cs.task_management.responses;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class SuccessResponse {
    private HttpStatus status;
    private String message;

    public SuccessResponse(String message, HttpStatus status) {
        this.status = status;
        this.message = message;
    }
}
