package de.zaeaep.taskhub.controller;

import de.zaeaep.taskhub.dto.TaskCreateRequest;
import de.zaeaep.taskhub.dto.TaskResponse;
import de.zaeaep.taskhub.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import de.zaeaep.taskhub.dto.TaskUpdateRequest;
import jakarta.validation.constraints.Size;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor

public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@Valid @RequestBody TaskCreateRequest request) {
        return taskService.create(request);
    }
    
    @GetMapping
    public List<TaskResponse> getAll() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public TaskResponse getById(@PathVariable long id) {
        return taskService.findById(id);
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable long id, @Valid @RequestBody TaskUpdateRequest request) {
        return taskService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        taskService.delete(id);
    }

}