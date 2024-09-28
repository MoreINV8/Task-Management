package ku.cs.task_management.services;

import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.Project;
import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.repositories.MemberRepository;
import ku.cs.task_management.repositories.ProjectRepository;
import ku.cs.task_management.requests.project_requests.ProjectCreateRequest;
import ku.cs.task_management.responses.ProjectResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public boolean isAccountExist(UUID memberId) {
        return memberRepository.findMemberByMemberId(memberId) != null;
    }

    // Get all projects by owner
    public List<ProjectResponse> getAllProjectsByOwnerId(UUID memberId) {
        List<ProjectResponse> responses = new ArrayList<>();
        List<Project> projects = projectRepository.findAllByProjectOwnerMemberId(memberId);
        for (Project project : projects) {
            responses.add(modelMapper.map(project, ProjectResponse.class));
        }
        return responses;
    }

    // Create a new project
    public ProjectResponse createProject(ProjectCreateRequest request)
            throws NotFoundMemberException {
        /**
        * highlight: need to recheck again about the exception
        * */
        Member member = memberRepository.findMemberByMemberId(request.getProjectOwnerId());
        String email = member.getDetail().getMemberEmail();

        if(!isAccountExist(request.getProjectOwnerId())) {
            throw new NotFoundMemberException(email);
        }
        Project project = new Project();

        project.setProjectName(request.getProjectName());
        project.setProjectDescription(request.getProjectDescription());
        project.setProjectDeadline(request.getProjectDeadline());

        Member projectOwner = memberRepository.findById(request.getProjectOwnerId()).orElseThrow(() -> new RuntimeException("Member not found"));
        project.setProjectOwner(projectOwner);

        Project createdProject = projectRepository.save(project);
        return modelMapper.map(createdProject, ProjectResponse.class);
    }
}

