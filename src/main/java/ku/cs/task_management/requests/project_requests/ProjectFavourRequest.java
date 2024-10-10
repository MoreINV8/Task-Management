package ku.cs.task_management.requests.project_requests;

import ku.cs.task_management.commons.ProjectStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class ProjectFavourRequest {
    private UUID projectId;
    private ProjectStatus projectFav;
}
