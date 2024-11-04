package ku.cs.task_management.repositories;

import ku.cs.task_management.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID> {

    @Query(
            value = "SELECT n FROM Notification n WHERE n.notificationReceiver.memberId = :id"
    )
    public List<Notification> getNotificationsByReceiverId(@Param("id") UUID receiveId);
}


