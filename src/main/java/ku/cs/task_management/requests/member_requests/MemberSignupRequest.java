package ku.cs.task_management.requests.member_requests;

import lombok.Data;

@Data
public class MemberSignupRequest {
    private String memberEmail;
    private String memberName;
    private String memberLastname;
    private String memberPassword;
    private String username;
    private String img;
}
