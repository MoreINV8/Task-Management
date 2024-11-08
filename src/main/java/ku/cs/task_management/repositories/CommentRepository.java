package ku.cs.task_management.repositories;

import ku.cs.task_management.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllByCommentTaskTaskId(UUID taskId);
}


