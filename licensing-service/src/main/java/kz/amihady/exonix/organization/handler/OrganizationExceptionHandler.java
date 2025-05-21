package kz.amihady.exonix.organization.handler;

import jakarta.servlet.http.HttpServletRequest;
import kz.amihady.exonix.handler.error.ApiError;
import kz.amihady.exonix.organization.handler.exception.OrganizationNotFoundException;
import kz.amihady.exonix.organization.handler.exception.OrganizationServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class OrganizationExceptionHandler {

    @ExceptionHandler(OrganizationNotFoundException.class)
    public ResponseEntity<ApiError> handle(
            OrganizationNotFoundException exception, HttpServletRequest request){
        log.error(exception.getMessage());
        var apiError =  ApiError.builder()
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .status(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .error(HttpStatus.NOT_FOUND)
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(apiError);
    }

    @ExceptionHandler(OrganizationServiceException.class)
    public ResponseEntity<ApiError> handle(
            OrganizationServiceException exception, HttpServletRequest request){
        log.error(exception.getMessage());
        var apiError =  ApiError.builder()
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .status(HttpStatus.BAD_REQUEST.value())
                .message(exception.getMessage())
                .error(HttpStatus.BAD_REQUEST)
                .build();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(apiError);
    }
}
