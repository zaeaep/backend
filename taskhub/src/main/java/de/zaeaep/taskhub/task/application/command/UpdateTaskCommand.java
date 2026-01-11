package de.zaeaep.taskhub.task.application.command;

public record UpdateTaskCommand(
    String title,
    String description,
    Boolean done
) {
    
}
