package kz.amihady.exonix.handler.error;

public record ValidationError(
        String field,
        String message
) {
}
