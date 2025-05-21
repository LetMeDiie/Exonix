package kz.amihady.exonix.organization.handler.error;

public record ValidationError(
        String field,
        String message
) {
}
