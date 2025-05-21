package kz.amihady.exonix.license.domain.model;
import kz.amihady.exonix.license.domain.enums.LicenseType;
import lombok.*;
import java.util.UUID;


@Getter
@Setter
@Builder
public class License {
    private UUID licenseId;
    private String description;
    private UUID organizationId;
    private String productName;
    private LicenseType licenseType;
}
