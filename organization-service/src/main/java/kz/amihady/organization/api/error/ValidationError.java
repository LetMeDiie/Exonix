package kz.amihady.organization.api.error;

public record ValidationError(
        String field,
        String message
) {
}
