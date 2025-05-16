package kz.amihady.license.license.api.response;

import kz.amihady.license.license.enums.LicenseType;
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
