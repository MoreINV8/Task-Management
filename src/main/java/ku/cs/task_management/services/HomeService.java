package ku.cs.task_management.services;

import ku.cs.task_management.entities.Participation;
import ku.cs.task_management.entities.RecentlyView;
import ku.cs.task_management.entities.Task;
import ku.cs.task_management.repositories.ParticipationRepository;
import ku.cs.task_management.repositories.RecentlyRepository;
import ku.cs.task_management.responses.HomeResponse;
import ku.cs.task_management.responses.RecentlyViewResponse;
import ku.cs.task_management.responses.TaskResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HomeService {

    @Autowired
    private RecentlyRepository recentlyRepository;
    @Autowired
    private ParticipationRepository participationRepository;

    public HomeResponse getHomePage(UUID memberId) {
        HomeResponse response = new HomeResponse();
        // get All RecentlyView
        List<RecentlyView> recentlyViews = recentlyRepository.findByMemberId(memberId)
                .stream()
                .sorted(Comparator.comparing(RecentlyView::getRecentlyTime))
                .collect(Collectors.toList());

        List<RecentlyViewResponse> recentlyViewResponses = new ArrayList<>();
        for (RecentlyView recentlyView : recentlyViews) {
            recentlyViewResponses.add(new RecentlyViewResponse(recentlyView));
        }
        // get All participate Task
        List<Participation> participationList = participationRepository.findAllByMemberMemberId(memberId);
        List<Task> tasks = participationList.stream()
                .map(Participation::getTask)
                .collect(Collectors.toList());
        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task task : tasks) {
            taskResponses.add(new TaskResponse(task));
        }
        response.setTasks(taskResponses);
        response.setRecentlyViews(recentlyViewResponses);
        return response;
    }


}
