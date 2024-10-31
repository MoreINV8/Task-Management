package ku.cs.task_management.responses;

import ku.cs.task_management.commons.ProjectStatus;
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
    private ProjectStatus projectFav;
    private UUID projectOwnerId;
    private String projectImg;
}
