package ku.cs.task_management.requests.member_requests;

import lombok.Data;

@Data
public class MemberLoginRequest {
    private String memberEmail;
    private String memberPassword;
}
