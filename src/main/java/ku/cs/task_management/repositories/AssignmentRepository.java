package ku.cs.task_management.repositories;

import ku.cs.task_management.entities.Assignment;
import ku.cs.task_management.entities.keys.AssignmentKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, AssignmentKey> {
    List<Assignment> findAllByProjectProjectId(UUID projectId);
}
