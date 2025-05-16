package kz.amihady.license.license.model;


import jakarta.persistence.*;
import kz.amihady.license.license.enums.LicenseType;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "licenses")
public class License  {
    @Id
    @GeneratedValue
    @Column(name = "license_id", nullable = false)
    private UUID licenseId;

    private String description;

    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Enumerated(EnumType.STRING)
    @Column(name = "license_type", nullable = false)
    private LicenseType licenseType;
}
