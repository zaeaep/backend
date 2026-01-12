package de.zaeaep.taskhub.user.persistence;

import de.zaeaep.taskhub.user.domain.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryUserRepository implements UserRepository{

    private final Map<Long, User> store = new LinkedHashMap<>();

    @Override
    public User save(User user) {
        store.put(user.id(), user);
        return user;
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<User> findAll() {
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

    @Override
    public boolean existsByEmail(String email) {
        return store.values().stream().anyMatch(u -> u.email().equalsIgnoreCase(email));
    }
    
}
