package de.zaeaep.taskhub.common.error;

import de.zaeaep.taskhub.task.domain.TaskNotFoundException;
import de.zaeaep.taskhub.user.domain.UserNotFoundException;
import de.zaeaep.taskhub.common.error.ApiException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Instant;
import java.util.List;



@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleViolation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ApiError.FieldViolation> violations = ex.getBindingResult().getFieldErrors().stream().map(err -> new ApiError.FieldViolation(err.getField(), err.getDefaultMessage())).toList();

        ApiError body = new ApiError(
            Instant.now(),
            400,
            "Bad Request",
            "Validation failed",
            request.getRequestURI(),
            "VALIDATION_FAILED",
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
            "VALIDATION_FAILED",
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
            "VALIDATION_FAILED",
            List.of()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpected(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error on {}: {}", request.getRequestURI(), ex.getMessage(), ex);

        ApiError body = new ApiError(
            Instant.now(),
            500,
            "Internal Server Error",
            "Unexpected error",
            request.getRequestURI(),
            "UNEXPECTED_ERROR",
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
            "VALIDATION_FAILED",
            List.of()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        var violations = ex.getConstraintViolations().stream().map(v -> new ApiError.FieldViolation(v.getPropertyPath().toString(), v.getMessage())).toList();

        ApiError body = new ApiError(
            Instant.now(),
            400,
            "Bad Request",
            "Validation Vailed",
            request.getRequestURI(),
            "VALIDATION_FAILED",
            violations
        );

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiError> handleApiException(ApiException ex, HttpServletRequest request) {

        int status = switch (ex.errorCode()) {
            case "TASK_NOT_FOUND", "USER_NOT_FOUND" -> 404;
            case "EMAIL_ALREADY_USED" -> 409;
            default -> 400;
        };

        String error = switch (status) {
            case 404 -> "NOT FOUND";
            case 409 -> "Conflict";
            default -> " Bad Request";
        };

        ApiError body = new ApiError(
            Instant.now(),
            status,
            error,
            ex.getMessage(),
            request.getRequestURI(),
            ex.errorCode(),
            List.of()
        );

        return ResponseEntity.status(status).body(body);
    }
    
}
