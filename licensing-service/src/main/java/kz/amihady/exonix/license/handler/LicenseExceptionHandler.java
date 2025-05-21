package kz.amihady.exonix.license.handler;


import jakarta.servlet.http.HttpServletRequest;
import kz.amihady.exonix.handler.error.ApiError;
import kz.amihady.exonix.license.handler.exception.LicenseNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class LicenseExceptionHandler {

    @ExceptionHandler(LicenseNotFoundException.class)
    public ResponseEntity<ApiError> handle(
            LicenseNotFoundException exception, HttpServletRequest request){
        log.error(exception.getMessage());
        var apiError =  ApiError.builder()
                    .timestamp(LocalDateTime.now())
                    .path(request.getRequestURI())
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(exception.getMessage())
                    .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiError);
    }
}
