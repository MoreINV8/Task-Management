package ku.cs.task_management.services;

import ku.cs.task_management.commons.TaskStatus;
import ku.cs.task_management.entities.Member;
import ku.cs.task_management.entities.Participation;
import ku.cs.task_management.entities.Project;
import ku.cs.task_management.entities.Task;
import ku.cs.task_management.entities.keys.ParticipationKey;
import ku.cs.task_management.exceptions.InvalidRequestException;
import ku.cs.task_management.exceptions.NotFoundMemberException;
import ku.cs.task_management.exceptions.NotFoundProjectException;
import ku.cs.task_management.exceptions.NotFoundTaskException;
import ku.cs.task_management.repositories.MemberRepository;
import ku.cs.task_management.repositories.ParticipationRepository;
import ku.cs.task_management.repositories.ProjectRepository;
import ku.cs.task_management.repositories.TaskRepository;
import ku.cs.task_management.requests.task_requests.TaskCreateRequest;
import ku.cs.task_management.requests.task_requests.TaskUpdateRequest;
import ku.cs.task_management.responses.TaskResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ParticipationRepository participationRepository;

    public List<TaskResponse> getAllTasks() {
        List<TaskResponse> responses = new ArrayList<>();
        for (Task task : taskRepository.findAll()) {
            responses.add(modelMapper.map(task, TaskResponse.class));
        }
        return responses;
    }

    // create task
    public TaskResponse createTask(TaskCreateRequest request)
            throws NotFoundProjectException, NotFoundMemberException {

        Task task = new Task();

        task.setTaskName(request.getTaskName());
        task.setTaskDetail(request.getTaskDetail());
        task.setTaskProject(projectRepository.findById(request.getTaskProjectId())
                .orElseThrow(() -> new NotFoundProjectException(request.getTaskProjectId())));
        task.setTaskStatus(TaskStatus.TODO);
        //set Date
        if (request.getTaskDueDate() != null)
            task.setTaskDueDate(request.getTaskDueDate());
        else
            task.setTaskDueDate(LocalDateTime.now());

        Task createdTask = taskRepository.save(task);

        Member member = memberRepository.findById(request.getTaskOwnerId())
                .orElseThrow(() -> new NotFoundMemberException(request.getTaskOwnerId()));

        ParticipationKey participationKey = new ParticipationKey(createdTask.getTaskId(), member.getMemberId());
        Participation participate = new Participation();
        participate.setId(participationKey);
        participate.setTask(createdTask);
        participate.setMember(member);
        participationRepository.save(participate);

        return modelMapper.map(createdTask, TaskResponse.class);
    }

    public List<TaskResponse> getAllTasksByProjectId(UUID projectId)
            throws NotFoundProjectException {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundProjectException(projectId));

        List<TaskResponse> responses = new ArrayList<>();
        for (Task task : taskRepository.findAllByTaskProjectProjectId(projectId)) {
            responses.add(modelMapper.map(task, TaskResponse.class));
        }

        return responses;
    }

    public TaskResponse updateTaskDetail(TaskUpdateRequest request)
            throws NotFoundTaskException, NotFoundProjectException {

        Task task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new NotFoundTaskException(request.getTaskId()));

        UUID projectId = request.getTaskProjectId();
        projectRepository.findById(projectId).orElseThrow(() -> new NotFoundProjectException(projectId));

        task.setTaskName(request.getTaskName());
        task.setTaskDetail(request.getTaskDetail());

        Task updatedTask = taskRepository.save(task);
        return modelMapper.map(updatedTask, TaskResponse.class);
    }

    public TaskResponse deleteTask(UUID projectId, UUID taskId)
            throws NotFoundProjectException, NotFoundTaskException {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundProjectException(projectId));

        // find the task within the project
        Task task = project.getProjectTasks().stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new NotFoundTaskException(taskId));

        // remove the task from the project
        project.getProjectTasks().remove(task);
        projectRepository.save(project);

        taskRepository.delete(task);

        return modelMapper.map(task, TaskResponse.class);
    }

    public TaskResponse getTaskDetail(UUID projectId, UUID taskId)
            throws NotFoundProjectException, NotFoundTaskException {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundProjectException(projectId));

        // find the task within the project
        Task task = project.getProjectTasks().stream()
                .filter(t -> t.getTaskId().equals(taskId))
                .findFirst()
                .orElseThrow(() -> new NotFoundTaskException(taskId));

        return modelMapper.map(task, TaskResponse.class);
    }

    // Method to change task status
    public TaskResponse changeTaskStatus(UUID taskId, TaskStatus newStatus)
            throws NotFoundTaskException {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NotFoundTaskException(taskId));

        task.setTaskStatus(newStatus);

        taskRepository.save(task);

        TaskResponse taskResponse = modelMapper.map(task, TaskResponse.class);
        taskResponse.setTaskStatus(task.getTaskStatus());
        return taskResponse;
    }
}
