package ku.cs.task_management.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectListResponse {
    List<ProjectResponse> ownedProject;
    List<ProjectResponse> participateProject;
}
