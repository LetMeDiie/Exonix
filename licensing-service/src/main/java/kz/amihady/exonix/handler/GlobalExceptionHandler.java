package kz.amihady.exonix.handler;

import jakarta.servlet.http.HttpServletRequest;
import kz.amihady.exonix.handler.error.ApiError;
import kz.amihady.exonix.handler.error.ApiValidationError;
import kz.amihady.exonix.handler.error.ValidationError;
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
    public ResponseEntity<ApiError> handleGeneralException
            (Exception ex, HttpServletRequest request) {
        log.error(ex.getMessage());

        var apiError =  ApiError.builder()
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage())
                .error(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(apiError);
    }
}
