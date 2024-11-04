package ku.cs.task_management.responses;

import ku.cs.task_management.entities.Assignment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class AssignResponse {
    private UUID memberId;
    private String memberName;
    private String memberLastName;
    private String role;
    private String img;

    public AssignResponse(Assignment assignment) {
        this.memberId = assignment.getMember().getMemberId();
        this.memberName = assignment.getMember().getDetail().getMemberName();
        this.memberLastName = assignment.getMember().getDetail().getMemberLastname();
        this.role = assignment.getRole();
        this.img = assignment.getMember().getDetail().getImg();
    }
}
