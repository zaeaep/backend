package de.zaeaep.taskhub.task.api.mapper;

import de.zaeaep.taskhub.task.api.dto.TaskResponse;
import de.zaeaep.taskhub.task.domain.Task;

public final class TaskMapper {

    private TaskMapper() {
    }

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
