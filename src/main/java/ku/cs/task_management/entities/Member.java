package ku.cs.task_management.entities;

import jakarta.persistence.*;
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

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", detail=" + detail +
                '}';
    }
}
