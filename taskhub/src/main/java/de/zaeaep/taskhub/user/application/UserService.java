package de.zaeaep.taskhub.user.application;

import de.zaeaep.taskhub.user.application.command.CreateUserCommand;
import de.zaeaep.taskhub.user.application.command.UpdateUserCommand;
import de.zaeaep.taskhub.user.domain.User;
import de.zaeaep.taskhub.user.domain.UserNotFoundException;
import de.zaeaep.taskhub.user.persistence.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final AtomicLong idGenerator = new AtomicLong(0);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User create(CreateUserCommand cmd) {
        long id = idGenerator.incrementAndGet();
        User user = new User(id, cmd.name(), cmd.email(), Instant.now());
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll().stream().sorted((a, b) -> Long.compare(a.id(), b.id())).toList();
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public User update(long id, UpdateUserCommand cmd) {
        User existing = findById(id);
        String newName = cmd.name() != null ? cmd.name() : existing.name();
        String newEmail = cmd.email() != null ? cmd.email() : existing.email();
        User updated = new User(existing.id(), newName, newEmail, existing.createdAt());
        return userRepository.save(updated);
    }

    public void delete(long id) {
        if (!userRepository.existsById(id)) throw new UserNotFoundException(id);
        userRepository.deleteById(id);
    }


}
