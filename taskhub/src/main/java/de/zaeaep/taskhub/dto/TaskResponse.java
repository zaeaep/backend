package de.zaeaep.taskhub.dto;

import java.time.Instant;

public record TaskResponse(
    long id,
    String title,
    String description,
    boolean done,
    Instant createdAt
) {}
