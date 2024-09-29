package ku.cs.task_management.repositories;

import ku.cs.task_management.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository <Project, UUID> {
    List<Project> findAllByProjectOwnerMemberId(UUID memberId);
}
