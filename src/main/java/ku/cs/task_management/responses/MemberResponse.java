package ku.cs.task_management.responses;

import ku.cs.task_management.entities.MemberDetail;
import lombok.Data;

import java.util.UUID;

@Data
public class MemberResponse {
    private UUID memberId;
    private MemberDetail detail;
}
