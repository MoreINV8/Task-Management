package ku.cs.task_management.repositories;

import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository <Project, UUID> {
    List<Project> findAllByProjectOwnerMemberId(UUID memberId);
}
