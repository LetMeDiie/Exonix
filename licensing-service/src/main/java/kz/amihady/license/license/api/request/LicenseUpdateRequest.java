package kz.amihady.license.license.api.request;

import jakarta.validation.constraints.Size;
import kz.amihady.license.license.enums.LicenseType;

public record LicenseUpdateRequest(

        @Size(min = 5, max = 100, message = "{license.description.size}")
        String description,

        @Size(min = 3, max = 50, message = "{license.productName.size}")
        String productName,

        LicenseType licenseType
) {}