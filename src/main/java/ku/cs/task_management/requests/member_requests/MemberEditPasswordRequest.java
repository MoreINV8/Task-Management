package ku.cs.task_management.requests.member_requests;

import lombok.Data;

import java.util.UUID;

@Data
public class MemberEditPasswordRequest {
    private UUID memberId;
    private String currentPassword;
    private String newPassword;
}
