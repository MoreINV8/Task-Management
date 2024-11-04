package ku.cs.task_management.utils;

import ku.cs.task_management.commons.NotificationType;
import ku.cs.task_management.exceptions.InvalidRequestException;
import ku.cs.task_management.repositories.MeetingRepository;
import ku.cs.task_management.repositories.ProjectRepository;
import ku.cs.task_management.repositories.TaskRepository;

import java.util.HashMap;
import java.util.Map;

public class NotificationHandlerFactory {
    private final Map<NotificationType, NotificationHandler> handlers;

    public NotificationHandlerFactory(ProjectRepository projectRepository,
                                      TaskRepository taskRepository,
                                      MeetingRepository meetingRepository) {
        handlers = new HashMap<>();
        handlers.put(NotificationType.PROJECT, new ProjectNotificationHandler(projectRepository));
        handlers.put(NotificationType.TASK, new TaskNotificationHandler(taskRepository));
        handlers.put(NotificationType.MEETING, new MeetingNotificationHandler(meetingRepository));
    }

    public NotificationHandler getHandler(NotificationType type) throws InvalidRequestException {
        NotificationHandler handler = handlers.get(type);
        if (handler == null) {
            throw new InvalidRequestException();
        }
        return handler;
    }
}
