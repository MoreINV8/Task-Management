package ku.cs.task_management.responses;

import lombok.Data;

import java.util.UUID;

@Data
public class AssignResponse {
    private UUID memberId;
    private String memberName;
    private String memberLastName;
    private String role;
    private String img;
}
