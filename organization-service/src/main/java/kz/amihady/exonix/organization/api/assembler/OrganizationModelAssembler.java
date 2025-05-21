package kz.amihady.exonix.organization.api.assembler;

import kz.amihady.exonix.organization.api.controller.OrganizationRestController;
import kz.amihady.exonix.organization.api.dto.response.OrganizationResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrganizationModelAssembler implements RepresentationModelAssembler<OrganizationResponse, EntityModel<OrganizationResponse>> {

    @Override
    public EntityModel<OrganizationResponse> toModel(OrganizationResponse organizationResponse) {
        UUID organizationId = UUID.fromString(organizationResponse.id());
        return EntityModel.of(
                organizationResponse,
                linkTo(methodOn(OrganizationRestController.class)
                        .getOrganization(organizationId, Locale.getDefault()))
                        .withSelfRel(),
                linkTo(methodOn(OrganizationRestController.class)
                        .updateOrganization(organizationId, null, Locale.getDefault()))
                        .withRel("update"),
                linkTo(methodOn(OrganizationRestController.class)
                        .deleteOrganization(organizationId, Locale.getDefault()))
                        .withRel("delete")
        );
    }
}
