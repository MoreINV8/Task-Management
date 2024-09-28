package ku.cs.task_management.repositories;

import ku.cs.task_management.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {

    @Query(
            value = "SELECT m FROM Member m WHERE m.detail.memberEmail = :email"
    )
    Member findMemberByEmail(@Param("email") String email);

    Member findMemberByMemberId(UUID memberId);

    @Query(
            value = "SELECT m.detail.memberEmail FROM Member m WHERE m.memberId = :id"
    )
    String findMemberEmailByMemberId(@Param("id") UUID memberId);
}
