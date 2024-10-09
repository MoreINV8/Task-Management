package ku.cs.task_management.repositories;

import ku.cs.task_management.entities.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LogRepository extends JpaRepository<Log, UUID> {

    @Query(
            value = "SELECT l FROM Log l WHERE l.project.projectId = :id"
    )
    List<Log> getLogsByProjectID(@Param("id") UUID id);
}
