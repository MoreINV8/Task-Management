package ku.cs.task_management.responses;

import lombok.Data;

import java.util.List;

@Data
public class HomeResponse {
    private List<KanBanResponse> tasks;
    private List<RecentlyViewResponse> recentlyViews;
}
