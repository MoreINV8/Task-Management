package ku.cs.task_management.repositories;

import ku.cs.task_management.entities.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, UUID> {
    List<Meeting> findAllByMeetingProjectProjectId(UUID projectId);
}
