package de.zaeaep.taskhub.repository;

import de.zaeaep.taskhub.model.Task;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryTaskRepository implements TaskRepository{
    private final Map<Long, Task> store = new LinkedHashMap<>();

    @Override
    public Task save(Task task) {
        store.put(task.id(), task);
        return task;
    }

    @Override
    public Optional<Task> findById(long id){
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public boolean existsById(long id) {
        return store.containsKey(id);
    }

    @Override
    public void deleteById(long id) {
        store.remove(id);
    }
}
