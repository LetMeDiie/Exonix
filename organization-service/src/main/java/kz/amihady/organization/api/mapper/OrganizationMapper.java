package kz.amihady.organization.api.mapper;

import kz.amihady.organization.api.request.OrganizationCreateRequest;
import kz.amihady.organization.api.response.OrganizationResponse;
import kz.amihady.organization.model.Organization;
import org.springframework.stereotype.Component;

@Component
public class OrganizationMapper {

    public Organization toOrganization(OrganizationCreateRequest request){
        return Organization.builder()
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
