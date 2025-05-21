package kz.amihady.exonix.organization.api.mapper;

import kz.amihady.exonix.organization.api.dto.request.OrganizationCreateRequest;
import kz.amihady.exonix.organization.api.dto.response.OrganizationResponse;
import kz.amihady.exonix.organization.domain.Organization;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrganizationApiMapper {

    public Organization toOrganization(
            OrganizationCreateRequest request, UUID organizationId){
        return Organization.builder()
                .id(organizationId)
                .name(request.name())
                .email(request.email())
                .phone(request.phone())
                .build();
    }

    public OrganizationResponse fromOrganization(Organization organization){
        return new OrganizationResponse(
                organization.getId().toString(),
                organization.getName(),
                organization.getEmail(),
                organization.getPhone()
        );
    }
}
