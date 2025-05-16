package kz.amihady.license.license.api.error;

public record ValidationError(
        String field,
        String message
) {
}
