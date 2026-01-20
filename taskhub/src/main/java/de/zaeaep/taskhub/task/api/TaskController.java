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
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor

public class TaskController {

    private final TaskService taskService;

    @PostMapping
    // @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskCreateRequest request) {
        Task created = taskService.create(new CreateTaskCommand(
            request.title(),
            request.description()
        ));
        TaskResponse response = TaskMapper.toResponse(created);

        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.id()).toUri();


        return ResponseEntity.created(location).body(response);
    }
    
    @GetMapping
    public List<TaskResponse> getAll() {
        return taskService.findAll().stream().map(TaskMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getById(@PathVariable @Positive(message = "id must be positiv") long id) {
        return ResponseEntity.ok(TaskMapper.toResponse(taskService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable @Positive(message = "id must be positiv") long id, @Valid @RequestBody TaskUpdateRequest request) {
        Task updated = taskService.update(id, new UpdateTaskCommand(request.title(), request.description(), request.done()));
        return ResponseEntity.ok(TaskMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive(message = "id must be positiv") long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

}