package kz.amihady.exonix.license.api.dto.response;

import kz.amihady.exonix.license.domain.enums.LicenseType;
import lombok.Builder;

@Builder
public record LicenseResponse(
        String licenseId,
        String organizationId,
        String description,
        String productName,
        LicenseType licenseType
) {
}
