package ku.cs.task_management.requests.member_requests.edit_profile;

import lombok.Data;

import java.util.UUID;

@Data
public class MemberEditProfileRequest {
    private UUID memberId;
    private MemberDetailEditProfileRequest detail;
}
