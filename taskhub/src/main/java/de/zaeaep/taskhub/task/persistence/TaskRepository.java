package de.zaeaep.taskhub.task.persistence;

import java.util.List;
import java.util.Optional;

import de.zaeaep.taskhub.task.domain.Task;

public interface TaskRepository {
    Task save(Task task);
    Optional<Task> findById(long id);
    List<Task> findAll();
    boolean existsById(long id);
    void deleteById(long id);
}

