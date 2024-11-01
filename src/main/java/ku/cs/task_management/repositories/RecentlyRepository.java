package ku.cs.task_management.repositories;

import ku.cs.task_management.entities.RecentlyView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecentlyRepository extends JpaRepository<RecentlyView, UUID> {
    @Query(
            value = "SELECT r FROM RecentlyView r WHERE r.recentViewer.memberId = :id ORDER BY r.recentlyTime"
    )
    List<RecentlyView> findByMemberId(@Param("id")UUID id);
}
