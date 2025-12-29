package de.zaeaep.taskhub.controller;

import de.zaeaep.taskhub.dto.TaskCreateRequest;
import de.zaeaep.taskhub.dto.TaskResponse;
import de.zaeaep.taskhub.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor

public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@RequestBody TaskCreateRequest request) {
        return taskService.create(request);
    } 
}