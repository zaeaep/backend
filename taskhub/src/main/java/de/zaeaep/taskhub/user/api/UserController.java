package de.zaeaep.taskhub.user.api;

import de.zaeaep.taskhub.user.api.dto.CreateUserRequest;
import de.zaeaep.taskhub.user.api.dto.UpdateUserRequest;
import de.zaeaep.taskhub.user.api.dto.UserResponse;
import de.zaeaep.taskhub.user.api.mapper.UserMapper;
import de.zaeaep.taskhub.user.application.UserService;
import de.zaeaep.taskhub.user.application.command.CreateUserCommand;
import de.zaeaep.taskhub.user.application.command.UpdateUserCommand;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    public final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody CreateUserRequest request) {
        return UserMapper.toResponse(userService.create(new CreateUserCommand(request.name(), request.email())));
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return userService.findAll().stream().map(UserMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable long id) {
        return UserMapper.toResponse(userService.findById(id));
    }

    @PutMapping("/{id}")
    public UserResponse update(@PathVariable long id, @Valid @RequestBody UpdateUserRequest request) {
        return UserMapper.toResponse(
            userService.update(id, new UpdateUserCommand(request.name(), request.email()))
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        userService.delete(id);
    }

}
