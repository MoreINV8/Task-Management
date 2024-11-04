package ku.cs.task_management.requests.log_request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogRequest {

    private String logAction;

    private UUID logProject;
    private UUID logActor;
}

