package de.zaeaep.taskhub.user.api.mapper;

import de.zaeaep.taskhub.user.api.dto.UserResponse;
import de.zaeaep.taskhub.user.domain.User;

public final class UserMapper {
    private UserMapper() {}

    public static UserResponse toResponse(User user) {
        return new UserResponse(user.id(), user.name(), user.email(), user.createdAt());
    }
    
}
