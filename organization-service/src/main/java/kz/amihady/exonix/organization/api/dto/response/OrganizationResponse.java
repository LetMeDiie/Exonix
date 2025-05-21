package kz.amihady.exonix.organization.api.dto.response;

public record OrganizationResponse(
        String id,
        String name,
        String email,
        String phone
) {
}
