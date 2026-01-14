package de.zaeaep.taskhub.user.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record CreateUserRequest(
    @NotBlank(message = "name is required")
    @Size(min = 2, max = 120, message = "name must be between 2 and 120 charakters")
    String name,
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email adress")
    @Size(max = 200, message = "Email must be at most 200 Charakters") 
    String email
) {}
