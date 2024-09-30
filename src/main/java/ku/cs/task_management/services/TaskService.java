package ku.cs.task_management.services;

import ku.cs.task_management.configs.TaskStatus;
import ku.cs.task_management.entities.Task;
import ku.cs.task_management.repositories.TaskRepository;
import ku.cs.task_management.requests.task_requests.TaskRequest;
import ku.cs.task_management.responses.TaskResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<TaskResponse> getAllTasks() {
        List<TaskResponse> responses = new ArrayList<>();
        for (Task task : taskRepository.findAll()) {
            responses.add(modelMapper.map(task, TaskResponse.class));
        }
        return responses;
    }

    // create task
    public TaskResponse createTask(TaskRequest request) {
        Task task = modelMapper.map(request, Task.class);
        task.setTaskStatus(TaskStatus.TODO);

        Task createdTask = taskRepository.save(task);
        return modelMapper.map(createdTask, TaskResponse.class);
    }
}
