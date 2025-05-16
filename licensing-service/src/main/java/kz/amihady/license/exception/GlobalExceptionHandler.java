package kz.amihady.license.exception;

import jakarta.servlet.http.HttpServletRequest;
import kz.amihady.license.license.api.error.ApiError;
import kz.amihady.license.license.api.error.ApiValidationError;
import kz.amihady.license.license.api.error.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(LicenseNotFoundException.class)
    public ResponseEntity<ApiError> handle(
            LicenseNotFoundException ex,
            HttpServletRequest request
    ){
        return handleException(ex,request,HttpStatus.NOT_FOUND,ex.getMessage());
    }

    @ExceptionHandler(OrganizationServiceException.class)
    public ResponseEntity<ApiError> handle(
            OrganizationServiceException ex,
            HttpServletRequest request
    ){
        return handleException(
                ex,
                request,
                ex.getStatus(),
                ex.getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiValidationError> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request,
            Locale locale
    ) {
        List<ValidationError> validationErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationError(
                        fieldError.getField(),
                        messageSource.getMessage(fieldError, locale)
                ))
                .toList();

        var errorDto = ApiValidationError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST)
                .message("Validation failed")
                .path(request.getRequestURI())
                .validations(validationErrors)
                .build();

        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(
            Exception ex,
            HttpServletRequest request
    ) {
        return handleException(ex,request,HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
    }

    private ResponseEntity<ApiError> handleException(
            Exception exception,
            HttpServletRequest request,
            HttpStatus status,
            String message) {

        log.error(exception.getMessage());

        var apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status)
                .message(message)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(apiError);
    }

}
