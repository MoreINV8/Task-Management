package ku.cs.task_management.repositories;

import ku.cs.task_management.entities.MemberDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDetailRepository extends JpaRepository<MemberDetail, String> {
    public MemberDetail findMemberDetailByMemberEmail(String email);
}
