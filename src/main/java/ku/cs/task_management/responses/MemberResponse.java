package ku.cs.task_management.responses;

import lombok.Data;

import java.util.UUID;

@Data
public class MemberResponse {
    private String token;
    private UUID memberId;
    private MemberDetailResponse detail;
}
