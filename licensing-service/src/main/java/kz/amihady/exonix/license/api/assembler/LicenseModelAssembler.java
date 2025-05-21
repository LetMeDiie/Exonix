package kz.amihady.exonix.license.api.assembler;


import kz.amihady.exonix.license.api.controller.LicenseRestController;
import kz.amihady.exonix.license.api.dto.response.LicenseResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Component
public class LicenseModelAssembler implements RepresentationModelAssembler<LicenseResponse, EntityModel<LicenseResponse>> {

    @Override
    public EntityModel<LicenseResponse> toModel(LicenseResponse license) {
        UUID orgId = UUID.fromString(license.organizationId());
        UUID licenseId = UUID.fromString(license.licenseId());

        return EntityModel.of(license,
                linkTo(methodOn(LicenseRestController.class)
                        .getLicense(orgId, licenseId, Locale.getDefault()))
                        .withSelfRel(),

                linkTo(methodOn(LicenseRestController.class)
                        .updateLicense(orgId, licenseId, null, Locale.getDefault()))
                        .withRel("update"),

                linkTo(methodOn(LicenseRestController.class)
                        .deleteLicense(orgId, licenseId, Locale.getDefault()))
                        .withRel("delete")
        );
    }
}
