package de.zaeaep.taskhub.user.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
    @Size(max = 120) String name,
    @Email @Size(max = 200) String email
) {}
