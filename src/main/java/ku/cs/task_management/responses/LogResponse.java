package ku.cs.task_management.responses;

import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class LogResponse {

    private String logAction;
    private Date logTime;

    private UUID logProject;
    private UUID logActor;
}
