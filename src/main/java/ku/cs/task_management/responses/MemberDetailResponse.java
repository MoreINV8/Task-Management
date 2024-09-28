package ku.cs.task_management.responses;

import lombok.Data;

@Data
public class MemberDetailResponse {
    private String memberEmail;
    private String memberName;
    private String memberLastname;
    private String username;
    private String img;
}
