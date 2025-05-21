package kz.amihady.exonix.handler.error;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ApiValidationError(
        LocalDateTime timestamp,
        Integer status,
        HttpStatus error,
        String message,
        String path,
        List<ValidationError> validations
) {}
