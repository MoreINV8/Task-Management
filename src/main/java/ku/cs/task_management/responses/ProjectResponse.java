package ku.cs.task_management.responses;

import ku.cs.task_management.commons.ProjectStatus;
import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.Project;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ProjectResponse {
    private UUID projectId;
    private String projectName;
    private String projectDescription;
    private Date projectDeadline;
    private ProjectStatus projectFav;
    private MemberResponse projectOwner;
    private String projectImg;

    public ProjectResponse(Project p) {
        projectId = p.getProjectId();
        projectName = p.getProjectName();
        projectDescription = p.getProjectDescription();
        projectDeadline = p.getProjectDeadline();
        projectFav = p.getProjectFav();
        projectImg = p.getProjectImg();
    }
}
