package ku.cs.task_management.requests.assignment_requests;

import lombok.Data;

import java.util.UUID;

@Data
public class AssignRequest {
    private UUID memberId;
    private String role;
}
