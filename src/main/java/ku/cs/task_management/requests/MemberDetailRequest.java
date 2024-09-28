package ku.cs.task_management.requests;

import lombok.Data;

@Data
public class MemberDetailRequest {
    private String memberEmail;
    private String memberName;
    private String memberLastname;
    private String memberPassword;
    private String username;
    private String img;
}
