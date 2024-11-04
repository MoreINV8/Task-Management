package ku.cs.task_management.utils;

import ku.cs.task_management.entities.Notification;
import ku.cs.task_management.exceptions.NotFoundTaskException;
import ku.cs.task_management.repositories.TaskRepository;

import java.util.UUID;

public class TaskNotificationHandler implements NotificationHandler {
    private final TaskRepository taskRepository;

    public TaskNotificationHandler(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void handle(Notification notification, UUID objectId) throws NotFoundTaskException {
        notification.setNotificationTask(
                taskRepository.findById(objectId)
                        .orElseThrow(() -> new NotFoundTaskException(objectId))
        );
    }
}
