package ku.cs.task_management.responses;

import ku.cs.task_management.entities.RecentlyView;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class RecentlyViewResponse {
    private UUID recentlyViewId;
    private String recentlyViewName;
    private UUID viewId;

    public RecentlyViewResponse(RecentlyView r) {
        this.recentlyViewId = r.getRecentlyId();
        this.recentlyViewName = r.getRecentlyProject().getProjectName();
        this.viewId = r.getRecentlyProject().getProjectId();
    }
}
