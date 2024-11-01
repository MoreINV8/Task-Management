package ku.cs.task_management.repositories;

import ku.cs.task_management.entities.RecentlyView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecentlyRepository {
    @Query(
            value = "SELECT r FROM RecentlyView r WHERE r.recentViewer.memberId = :id"
    )
    List<RecentlyView> findByMemberId(@Param("id")UUID id);
}
