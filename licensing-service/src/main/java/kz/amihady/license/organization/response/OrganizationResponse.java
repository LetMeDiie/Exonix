package kz.amihady.license.organization.response;

import java.util.UUID;

public record OrganizationResponse(
        UUID id,
        String name,
        String email,
        String phone
) {
}
