package de.zaeaep.taskhub.user.domain;

import java.time.Instant;

public record User(
    long id,
    String name,
    String email,
    Instant createdAt
) {
    
}
