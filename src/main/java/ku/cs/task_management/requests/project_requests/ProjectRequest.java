package ku.cs.task_management.requests.project_requests;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ProjectRequest {
    private UUID ProjectId;
    private String projectName;
    private String projectDescription;
    private Date projectDeadline;
    private UUID projectOwnerId;
}