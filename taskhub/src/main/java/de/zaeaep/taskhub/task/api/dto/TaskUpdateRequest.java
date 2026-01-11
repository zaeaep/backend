package de.zaeaep.taskhub.task.api.dto;

import jakarta.validation.constraints.Size;

public record TaskUpdateRequest(
    @Size(max = 120, message = "title must be at most 120 characters")
    String title,

    @Size(max = 1000, message = "description must be at most 1000 characers")
    String description,

    Boolean done

){}
