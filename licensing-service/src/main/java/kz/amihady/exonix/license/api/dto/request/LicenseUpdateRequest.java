package kz.amihady.exonix.license.api.dto.request;

import jakarta.validation.constraints.Size;
import kz.amihady.exonix.license.domain.enums.LicenseType;

public record LicenseUpdateRequest(

        @Size(min = 5, max = 100, message = "{license.description.size}")
        String description,

        @Size(min = 3, max = 50, message = "{license.productName.size}")
        String productName,

        LicenseType licenseType
) {}