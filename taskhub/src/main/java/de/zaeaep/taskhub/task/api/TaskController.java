package de.zaeaep.taskhub.task.api;

import de.zaeaep.taskhub.task.api.dto.TaskCreateRequest;
import de.zaeaep.taskhub.task.api.dto.TaskResponse;
import de.zaeaep.taskhub.task.api.dto.TaskUpdateRequest;
import de.zaeaep.taskhub.task.api.mapper.TaskMapper;
import de.zaeaep.taskhub.task.application.TaskService;
import de.zaeaep.taskhub.task.application.command.CreateTaskCommand;
import de.zaeaep.taskhub.task.application.command.UpdateTaskCommand;
import de.zaeaep.taskhub.task.domain.Task;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor

public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@Valid @RequestBody TaskCreateRequest request) {
        Task created = taskService.create(new CreateTaskCommand(
            request.title(),
            request.description()
        ));
        return TaskMapper.toResponse(created);
    }
    
    @GetMapping
    public List<TaskResponse> getAll() {
        return taskService.findAll().stream().map(TaskMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public TaskResponse getById(@PathVariable @Positive(message = "id must be positiv") long id) {
        return TaskMapper.toResponse(taskService.findById(id));
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable @Positive(message = "id must be positiv") long id, @Valid @RequestBody TaskUpdateRequest request) {
        Task updated = taskService.update(id, new UpdateTaskCommand(request.title(), request.description(), request.done()));
        return TaskMapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Positive(message = "id must be positiv") long id) {
        taskService.delete(id);
    }

}