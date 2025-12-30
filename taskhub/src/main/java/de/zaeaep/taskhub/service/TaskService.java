package de.zaeaep.taskhub.service;

import de.zaeaep.taskhub.dto.TaskCreateRequest;
import de.zaeaep.taskhub.dto.TaskResponse;
import de.zaeaep.taskhub.exception.TaskNotFoundException;
import de.zaeaep.taskhub.model.Task;
import de.zaeaep.taskhub.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
//import java.util.LinkedHashMap;
import java.util.List;
//import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final AtomicLong idGenerator = new AtomicLong(0);
    //private final Map<Long, Task> tasks = new LinkedHashMap<>();

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskResponse create(TaskCreateRequest request) {
        long id = idGenerator.incrementAndGet();

        Task task = new Task(
            id,
            request.title(),
            request.description(),
            false,
            Instant.now()
        );

        taskRepository.save(task);  // here we save the task in the HashMap, later DB

        return toResponse(task);
    }

    public List<TaskResponse> findAll() {
        return taskRepository.findAll().stream().sorted((a, b) -> Long.compare(a.id(), b.id())).map(this::toResponse).toList();
    }

    public TaskResponse findById(long id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
        return toResponse(task);
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
            task.id(),
            task.title(),
            task.description(),
            task.done(),
            task.createdAt()
        );

    }


}
