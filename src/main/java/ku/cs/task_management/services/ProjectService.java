package ku.cs.task_management.services;

import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.Project;
import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.exceptions.NotFoundProjectException;
import ku.cs.task_management.repositories.MemberRepository;
import ku.cs.task_management.repositories.ProjectRepository;
import ku.cs.task_management.requests.project_requests.ProjectRequest;
import ku.cs.task_management.responses.ProjectResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public boolean isProjectExist(UUID projectId) {return projectRepository.findById(projectId).isPresent();}

    // Get all projects by owner
    public List<ProjectResponse> getAllProjectsByOwnerId(UUID memberId)
        throws NotFoundMemberException {

        Member member = memberRepository.findMemberByMemberId(memberId);
        String email = member.getDetail().getMemberEmail();

        if(!isAccountExist(memberId)) {
            throw new NotFoundMemberException(email);
        }

        List<ProjectResponse> responses = new ArrayList<>();
        List<Project> projects = projectRepository.findAllByProjectOwnerMemberId(memberId);
        for (Project project : projects) {
            responses.add(modelMapper.map(project, ProjectResponse.class));
        }
        return responses;
    }

    // Create a new project
    public ProjectResponse createProject(ProjectRequest request)
            throws NotFoundMemberException {

        //TODO: need to recheck again about the exception

        Member member = memberRepository.findMemberByMemberId(request.getProjectOwnerId());
        String email = member.getDetail().getMemberEmail();

        if(!isAccountExist(member.getMemberId())) {
            throw new NotFoundMemberException(email);
        }
        Project project = new Project();

        project.setProjectName(request.getProjectName());
        project.setProjectDescription(request.getProjectDescription());
        project.setProjectDeadline(request.getProjectDeadline());

        project.setProjectOwner(member);

        Project createdProject = projectRepository.save(project);
        return modelMapper.map(createdProject, ProjectResponse.class);
    }

    // update exist project
    public ProjectResponse updateProject(ProjectRequest request)
        throws NotFoundProjectException {

        if (!isProjectExist(request.getProjectId())) {
            throw new NotFoundProjectException();
        }

        Project existingProject = projectRepository.findById(request.getProjectId()).get();

        existingProject.setProjectName(request.getProjectName());
        existingProject.setProjectDescription(request.getProjectDescription());
        existingProject.setProjectDeadline(request.getProjectDeadline());

        Project updatedProject = projectRepository.save(existingProject);

        return modelMapper.map(updatedProject, ProjectResponse.class);
    }
}

