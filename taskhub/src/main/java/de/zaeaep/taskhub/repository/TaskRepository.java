package de.zaeaep.taskhub.repository;

import de.zaeaep.taskhub.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);
    Optional<Task> findById(long id);
    List<Task> findAll();
    boolean existsById(long id);
    void deleteById(long id);
}

