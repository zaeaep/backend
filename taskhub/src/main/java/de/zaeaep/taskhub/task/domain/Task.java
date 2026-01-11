package de.zaeaep.taskhub.task.domain;

import java.time.Instant;

public record Task(
    long id,
    String title,
    String description,
    boolean done,
    Instant createdAt

){}


