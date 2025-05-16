package kz.amihady.license.license.api.error;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record ApiError(
        LocalDateTime timestamp,
        Integer status,
        HttpStatus error,
        String message,
        String path
) {
}
