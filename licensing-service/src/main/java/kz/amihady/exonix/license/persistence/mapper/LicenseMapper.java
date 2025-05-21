package kz.amihady.exonix.license.persistence.mapper;

import kz.amihady.exonix.license.domain.model.License;
import org.springframework.stereotype.Component;

@Component
public class LicenseMapper {

    public License toDomain (kz.amihady.exonix.license.persistence.entity.LicenseEntity licenseEntity){
        return License.builder()
                .licenseId(licenseEntity.getLicenseId())
                .licenseType(licenseEntity.getLicenseType())
                .organizationId(licenseEntity.getOrganizationId())
                .productName(licenseEntity.getProductName())
                .description(licenseEntity.getDescription())
                .build();
    }

    public kz.amihady.exonix.license.persistence.entity.LicenseEntity toEntity(License license){
        return kz.amihady.exonix.license.persistence.entity.LicenseEntity.builder()
                .licenseId(license.getLicenseId())
                .licenseType(license.getLicenseType())
                .organizationId(license.getOrganizationId())
                .productName(license.getProductName())
                .description(license.getDescription())
                .build();
    }
}
