package de.zaeaep.taskhub.service;

import de.zaeaep.taskhub.dto.TaskResponse;
import de.zaeaep.taskhub.model.Task;

public class TaskMapper {
    public static TaskResponse toResponse(Task task) {
        return new TaskResponse(
            task.id(),
            task.title(),
            task.description(),
            task.done(),
            task.createdAt()
        );
    }
    
}
