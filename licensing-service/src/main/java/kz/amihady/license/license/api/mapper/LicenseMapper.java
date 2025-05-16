package kz.amihady.license.license.api.mapper;

import kz.amihady.license.license.api.request.LicenseCreateRequest;
import kz.amihady.license.license.api.response.LicenseResponse;
import kz.amihady.license.license.model.License;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class LicenseMapper {

    public License toLicense (LicenseCreateRequest request, UUID organizationId){
        return License.builder()
                .licenseType(request.licenseType())
                .productName(request.productName())
                .description(request.description())
                .organizationId(organizationId)
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
