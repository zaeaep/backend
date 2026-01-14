package de.zaeaep.taskhub.task.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskCreateRequest(
    @NotBlank(message = "{task.title.required}")
    @Size(min = 1, max = 120, message = "title must be betwween 1 and 120 Charakters")
    String title,

    @Size(max = 1000, message = "description must be at most 1000 Charakters")
    String description
) {}
