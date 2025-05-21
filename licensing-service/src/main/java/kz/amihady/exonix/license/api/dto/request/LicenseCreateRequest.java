package kz.amihady.exonix.license.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kz.amihady.exonix.license.domain.enums.LicenseType;

public record LicenseCreateRequest(
        @NotBlank(message = "{license.description.notBlank}")
        @Size(min = 5, max = 100, message = "{license.description.size}")
        String description,

        @NotBlank(message = "{license.productName.notBlank}")
        @Size(min = 3, max = 50, message = "{license.productName.size}")
        String productName,

        @NotNull(message = "{license.licenseType.notNull}")
        LicenseType licenseType
) {}