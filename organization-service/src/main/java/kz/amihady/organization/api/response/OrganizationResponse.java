package kz.amihady.organization.api.response;

public record OrganizationResponse(
        String id,
        String name,
        String email,
        String phone
) {
}
