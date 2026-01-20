package de.zaeaep.taskhub.user.api;

import de.zaeaep.taskhub.user.api.dto.CreateUserRequest;
import de.zaeaep.taskhub.user.api.dto.UpdateUserRequest;
import de.zaeaep.taskhub.user.api.dto.UserResponse;
import de.zaeaep.taskhub.user.api.mapper.UserMapper;
import de.zaeaep.taskhub.user.application.UserService;
import de.zaeaep.taskhub.user.application.command.CreateUserCommand;
import de.zaeaep.taskhub.user.application.command.UpdateUserCommand;
import de.zaeaep.taskhub.user.domain.User;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.util.List;

@Validated
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    
    public final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        User created = userService.create(new CreateUserCommand(request.name(), request.email()));
        UserResponse response = UserMapper.toResponse(created);

        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return userService.findAll().stream().map(UserMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable @Positive(message = "id must be positiv") long id) {
        return ResponseEntity.ok(UserMapper.toResponse(userService.findById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable @Positive(message = "id must be positiv") long id, @Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(UserMapper.toResponse(
            userService.update(id, new UpdateUserCommand(request.name(), request.email()))
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive(message = "id must be positiv") long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
