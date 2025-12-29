package de.zaeaep.taskhub.model;

import java.time.Instant;

public record Task(
    long id,
    String title,
    String description,
    boolean done,
    Instant createdAt

){}


