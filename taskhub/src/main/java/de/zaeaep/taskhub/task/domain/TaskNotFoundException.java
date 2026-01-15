package de.zaeaep.taskhub.task.domain;

import de.zaeaep.taskhub.common.error.ApiException;


// import org.springframework.http.HttpStatus;
// import org.springframework.web.bind.annotation.ResponseStatus;


// @ResponseStatus(HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends ApiException {
    public TaskNotFoundException(long id) {
        super("TASK_NOT_FOUND", "Task with id " + id + " not found");
    }
}
