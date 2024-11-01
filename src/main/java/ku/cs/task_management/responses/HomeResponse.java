package ku.cs.task_management.responses;

import lombok.Data;

import java.util.List;

@Data
public class HomeResponse {
    private List<TaskResponse> tasks;
    private List<RecentlyViewResponse> recentlyViews;
}
