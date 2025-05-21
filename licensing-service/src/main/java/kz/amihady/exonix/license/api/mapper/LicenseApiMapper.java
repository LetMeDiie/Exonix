package kz.amihady.exonix.license.api.mapper;

import kz.amihady.exonix.license.api.dto.request.LicenseCreateRequest;
import kz.amihady.exonix.license.api.dto.response.LicenseResponse;
import kz.amihady.exonix.license.domain.model.License;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LicenseApiMapper {

    public License toLicense (LicenseCreateRequest request, UUID organizationId, UUID licenseId){
        return License.builder()
                .licenseType(request.licenseType())
                .productName(request.productName())
                .description(request.description())
                .organizationId(organizationId)
                .licenseId(licenseId)
                .build();
    }

    public LicenseResponse fromLicense(License license){
        return LicenseResponse.builder()
                .licenseId(license.getLicenseId().toString())
                .organizationId(license.getOrganizationId().toString())
                .licenseType(license.getLicenseType())
                .productName(license.getProductName())
                .description(license.getDescription())
                .build();
    }
}
