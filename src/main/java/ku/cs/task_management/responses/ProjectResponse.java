package ku.cs.task_management.responses;

import ku.cs.task_management.entities.Member;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ProjectResponse {
    private UUID projectId;
    private String projectName;
    private String projectDescription;
    private Date projectDeadline;
    private UUID projectOwnerId;
}
