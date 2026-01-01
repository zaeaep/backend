package de.zaeaep.taskhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskCreateRequest(
    @NotBlank(message = "title must not be blank")
    String title,

    @Size(max = 1000, message = "description must be at most 1000 Charakters")
    String description
) {}
