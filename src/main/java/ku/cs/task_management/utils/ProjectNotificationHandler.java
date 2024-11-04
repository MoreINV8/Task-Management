package ku.cs.task_management.utils;

import ku.cs.task_management.entities.Notification;
import ku.cs.task_management.exceptions.NotFoundProjectException;
import ku.cs.task_management.repositories.ProjectRepository;

import java.util.UUID;

public class ProjectNotificationHandler implements NotificationHandler {
    private final ProjectRepository projectRepository;

    public ProjectNotificationHandler(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void handle(Notification notification, UUID objectId) throws NotFoundProjectException {
        notification.setNotificationProject(
                projectRepository.findById(objectId)
                        .orElseThrow(() -> new NotFoundProjectException(objectId))
        );
    }
}
