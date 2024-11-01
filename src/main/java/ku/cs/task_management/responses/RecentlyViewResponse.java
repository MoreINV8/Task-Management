package ku.cs.task_management.responses;

import ku.cs.task_management.commons.RecentlyType;
import ku.cs.task_management.entities.RecentlyView;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class RecentlyViewResponse {
    private UUID recentlyViewId;
    private String recentlyViewName;
    private RecentlyType recentlyType;
    private UUID viewId;

    public RecentlyViewResponse(RecentlyView r) {
        this.recentlyViewId = r.getRecentlyId();
        switch (r.getRecentlyType()) {
            case PROJECT :
                this.recentlyType = RecentlyType.PROJECT;
                this.recentlyViewName = r.getRecentlyProject().getProjectName();
                this.viewId = r.getRecentlyProject().getProjectId();
                break;
            case TASK :
                this.recentlyType = RecentlyType.TASK;
                this.recentlyViewName = r.getRecentlyTask().getTaskName();
                this.viewId = r.getRecentlyTask().getTaskId();
                break;
        }
    }
}
