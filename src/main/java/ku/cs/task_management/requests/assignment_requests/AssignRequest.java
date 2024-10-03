package ku.cs.task_management.requests.assignment_requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignRequest {
    private UUID memberId;
    private String role;
}
