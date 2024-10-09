package ku.cs.task_management.requests.log_request;

import lombok.Data;

import java.util.UUID;

@Data
public class LogRequest {

    private String logAction;

    private UUID logProject;
    private UUID logActor;
}

