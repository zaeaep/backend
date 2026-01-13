package de.zaeaep.taskhub.common.error;

import de.zaeaep.taskhub.task.domain.TaskNotFoundException;
import de.zaeaep.taskhub.user.domain.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleViolation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ApiError.FieldViolation> violations = ex.getBindingResult().getFieldErrors().stream().map(err -> new ApiError.FieldViolation(err.getField(), err.getDefaultMessage())).toList();

        ApiError body = new ApiError(
            Instant.now(),
            400,
            "Bad Request",
            "Validation failed",
            request.getRequestURI(),
            violations
        );

        return ResponseEntity.badRequest().body(body);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleBadJson(HttpMessageNotReadableException ex, HttpServletRequest request) {

        ApiError body = new ApiError(
            Instant.now(),
            400,
            "Bad Request",
            "Malformed JSON request",
            request.getRequestURI(),
            List.of()
        );
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(TaskNotFoundException ex, HttpServletRequest request) {
        
        ApiError body = new ApiError(
            Instant.now(),
            404,
            "Not Found",
            ex.getMessage(),
            request.getRequestURI(),
            List.of()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(Exception ex, HttpServletRequest request) {
        ApiError body = new ApiError(
            Instant.now(),
            500,
            "Internal Server Error",
            "Unexpected error",
            request.getRequestURI(),
            List.of()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
        ApiError body = new ApiError(
            Instant.now(),
            404,
            "Not Found",
            ex.getMessage(),
            request.getRequestURI(),
            List.of()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }
}
