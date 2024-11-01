package ku.cs.task_management.repositories;

import ku.cs.task_management.entities.Participation;
import ku.cs.task_management.entities.Task;
import ku.cs.task_management.entities.keys.ParticipationKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ParticipationRepository extends JpaRepository<Participation, ParticipationKey> {
    List<Participation> findAllByTaskTaskId(UUID id);

    List<Participation> findAllByMemberMemberId(UUID id);
}
