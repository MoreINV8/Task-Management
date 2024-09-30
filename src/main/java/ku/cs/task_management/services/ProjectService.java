package ku.cs.task_management.services;

import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.Project;
import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.exceptions.NotFoundProjectException;
import ku.cs.task_management.exceptions.NotProjectOwnerException;
import ku.cs.task_management.repositories.MemberRepository;
import ku.cs.task_management.repositories.ProjectRepository;
import ku.cs.task_management.requests.project_requests.ProjectRequest;
import ku.cs.task_management.responses.ProjectResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    // check if member is existed by his id.
    public boolean isAccountExist(UUID memberId) {
        return memberRepository.findMemberByMemberId(memberId) != null;
    }

    public List<ProjectResponse> getAllProjectsByOwnerId(UUID memberId) throws NotFoundMemberException {

        Member member = memberRepository.findMemberByMemberId(memberId);
        if (!isAccountExist(member.getMemberId())){
            throw new NotFoundMemberException(member.getDetail().getMemberEmail());
        }

        return getProjectsForMember(member);
    }

    public List<ProjectResponse> getAllProjectsByOwnerEmail(String email) throws NotFoundMemberException {

        Member member = memberRepository.findMemberByEmail(email);
        if (!isAccountExist(member.getMemberId())){
            throw new NotFoundMemberException(email);
        }

        return getProjectsForMember(member);
    }

    private List<ProjectResponse> getProjectsForMember(Member member) {
        List<Project> projects = projectRepository.findAllByProjectOwnerMemberId(member.getMemberId());

        // Map the project list to ProjectResponse using Java Streams for conciseness
        return projects.stream()
                .map(project -> modelMapper.map(project, ProjectResponse.class))
                .collect(Collectors.toList());
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
    public ProjectResponse updateProject(UUID projectId, ProjectRequest request, String email)
            throws NotFoundMemberException, NotFoundProjectException, NotProjectOwnerException {

        Member member = memberRepository.findMemberByEmail(email);
        if (member == null) {
            throw new NotFoundMemberException(email);
        }
        if (!member.getMemberId().equals(request.getProjectOwnerId())) {
            throw new NotProjectOwnerException(member.getMemberId(), request.getProjectOwnerId());
        }
        // Check if project exists and belongs to the member
        Project project = projectRepository.findById(projectId)
                .filter(p -> p.getProjectOwner().getMemberId().equals(member.getMemberId()))
                .orElseThrow(() -> new NotFoundProjectException(projectId));

        project.setProjectName(request.getProjectName());
        project.setProjectDescription(request.getProjectDescription());
        project.setProjectDeadline(request.getProjectDeadline());

        Project updatedProject = projectRepository.save(project);
        return modelMapper.map(updatedProject, ProjectResponse.class);
    }

    public ProjectResponse getProjectDetail(String email, UUID projectId) throws NotFoundMemberException, NotFoundProjectException, NotProjectOwnerException {

        Member member = memberRepository.findMemberByEmail(email);

        if (member == null) {
            throw new NotFoundMemberException(email);
        }

        Project project = projectRepository.findById(projectId)
                .filter(p -> p.getProjectOwner().getMemberId().equals(member.getMemberId()))
                .orElseThrow(() -> new NotFoundProjectException(projectId));

        if (!member.getMemberId().equals(project.getProjectOwner().getMemberId())) {
            throw new NotProjectOwnerException(member.getMemberId(), project.getProjectOwner().getMemberId());
        }

        return modelMapper.map(project, ProjectResponse.class);
    }
}

