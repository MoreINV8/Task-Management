package ku.cs.task_management.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "member_detail")
public class MemberDetail {

    @Id
    @Column(name = "member_email")
    private String memberEmail;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "member_lastname")
    private String memberLastname;

    @Column(name = "member_password")
    private String memberPassword;

    @Column(name = "username")
    private String username;

    @Column(name = "image_path")
    private String img;

    @Override
    public String toString() {
        return "MemberDetail{" +
                "memberEmail='" + memberEmail + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberLastname='" + memberLastname + '\'' +
                ", memberPassword='" + memberPassword + '\'' +
                ", username='" + username + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
