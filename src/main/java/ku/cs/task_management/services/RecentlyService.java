package ku.cs.task_management.services;

import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.Project;
import ku.cs.task_management.entities.RecentlyView;
import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.exceptions.NotFoundProjectException;
import ku.cs.task_management.repositories.MemberRepository;
import ku.cs.task_management.repositories.ProjectRepository;
import ku.cs.task_management.repositories.RecentlyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class RecentlyService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private RecentlyRepository recentlyRepository;

    public List<RecentlyView> findRecently(UUID memberId) throws NotFoundMemberException {
        if (!memberRepository.existsById(memberId)) {
            throw new NotFoundMemberException(memberId);
        }

        return recentlyRepository.findByMemberId(memberId);
    }

    public void deleteLastRecently(UUID memberId) throws NotFoundMemberException {
        List<RecentlyView> recentlyViews = findRecently(memberId);

        RecentlyView recentlyView = recentlyViews.get(recentlyViews.size() - 1); // last recently object

        recentlyView.setRecentlyTask(null);
        recentlyView.setRecentlyProject(null);
        recentlyView.setRecentViewer(null);

        recentlyRepository.save(recentlyView);
        recentlyRepository.delete(recentlyView);
    }

    public void saveRecently(UUID viewerId, UUID projectId) throws NotFoundMemberException, NotFoundProjectException {

        Member viewer = memberRepository.findById(viewerId)
                .orElseThrow(() -> new NotFoundMemberException(viewerId));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundProjectException(projectId));

        if (recentlyRepository.countByViewerId(viewerId) == 10) {
            deleteLastRecently(viewerId);
        }

        RecentlyView recentlyView = recentlyRepository.findByViewerAndProjectId(viewerId, projectId);
        if (recentlyView == null) {
            recentlyView = new RecentlyView();

            recentlyView.setRecentViewer(viewer);
            recentlyView.setRecentlyProject(project);
            recentlyView.setRecentlyTime(LocalDateTime.now());
        } else {
            recentlyView.setRecentlyTime(LocalDateTime.now());
        }

        recentlyRepository.save(recentlyView);
    }
}
