package ku.cs.task_management.repositories;

import ku.cs.task_management.entities.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MeetingRepository extends JpaRepository<Meeting, UUID> {
}
