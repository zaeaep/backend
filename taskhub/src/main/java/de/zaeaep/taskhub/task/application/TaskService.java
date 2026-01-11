package de.zaeaep.taskhub.task.application;

// import de.zaeaep.taskhub.task.api.dto.TaskCreateRequest;
// import de.zaeaep.taskhub.task.api.dto.TaskResponse;
// import de.zaeaep.taskhub.task.api.dto.TaskUpdateRequest;
// import de.zaeaep.taskhub.task.api.mapper.TaskMapper;
import de.zaeaep.taskhub.task.application.command.CreateTaskCommand;
import de.zaeaep.taskhub.task.application.command.UpdateTaskCommand;
import de.zaeaep.taskhub.task.domain.Task;
import de.zaeaep.taskhub.task.domain.TaskNotFoundException;
import de.zaeaep.taskhub.task.persistence.TaskRepository;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final AtomicLong idGenerator = new AtomicLong(0);
    //private final Map<Long, Task> tasks = new LinkedHashMap<>();

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task create(CreateTaskCommand cmd) {
        long id = idGenerator.incrementAndGet();

        Task task = new Task(
            id,
            cmd.title(),
            cmd.description(),
            false,
            Instant.now()
        );

        taskRepository.save(task);  // here we save the task in the HashMap, later DB

        return task;
    }

    public Task update(long id, UpdateTaskCommand cmd) {
        Task existing = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));

        String newTitle = cmd.title() != null ? cmd.title() : existing.title();
        String newDescription = cmd.description() != null ? cmd.description() : existing.description();
        boolean newDone = cmd.done() != null ? cmd.done() : existing.done();

        Task updated = new Task(
            existing.id(),
            newTitle,
            newDescription,
            newDone,
            existing.createdAt()
        );

        taskRepository.save(updated);
        return updated;
    }

    public void delete(long id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }

    public List<Task> findAll() {
        return taskRepository.findAll().stream().sorted((a, b) -> Long.compare(a.id(), b.id())).toList();
    }

    public Task findById(long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
    }

    // private Task toResponse(Task task) {
    //     return new TaskResponse(
    //         task.id(),
    //         task.title(),
    //         task.description(),
    //         task.done(),
    //         task.createdAt()
    //     );

    // }


}
