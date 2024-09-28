package ku.cs.task_management.entities;

import jakarta.persistence.*;
import ku.cs.task_management.requests.member_requests.edit_profile.MemberEditProfileRequest;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private UUID memberId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email_fk")
    private MemberDetail detail;

    public void updateMemberDetail(MemberDetail detail) {
        // set password to latest
        detail.setMemberPassword(this.detail.getMemberPassword());

        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", detail=" + detail +
                '}';
    }
}
