package de.zaeaep.taskhub.user.persistence;

import de.zaeaep.taskhub.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(long id);
    List<User> findAll();
    boolean existsById(long id);
    void deleteById(long id);
    boolean existsByEmail(String email);
}