package ku.cs.task_management.responses;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class LogResponse {

    private String logAction;
    private LocalDateTime logTime;

    private UUID logProject;
    private UUID logActor;
}
