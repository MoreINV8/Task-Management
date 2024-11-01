package ku.cs.task_management.services;

import ku.cs.task_management.commons.RecentlyType;
import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.Project;
import ku.cs.task_management.entities.RecentlyView;
import ku.cs.task_management.entities.Task;
import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.repositories.MemberRepository;
import ku.cs.task_management.repositories.RecentlyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RecentlyService {

    @Autowired
    private MemberRepository memberRepository;

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

    public void saveRecently(Member viewer, Object object, RecentlyType type) {
        RecentlyView recentlyView = new RecentlyView();
        recentlyView.setRecentlyType(type);
        recentlyView.setRecentViewer(viewer);

        switch (type) {
            case PROJECT -> recentlyView.setRecentlyProject((Project) object);
            case TASK -> recentlyView.setRecentlyTask((Task) object);
        }

        recentlyRepository.save(recentlyView);
    }
}
