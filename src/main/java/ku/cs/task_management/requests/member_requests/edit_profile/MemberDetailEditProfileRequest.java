package ku.cs.task_management.requests.member_requests.edit_profile;

import lombok.Data;

@Data
public class MemberDetailEditProfileRequest {
    private String memberEmail;
    private String memberName;
    private String memberLastname;
    private String username;
    private String img;
}
