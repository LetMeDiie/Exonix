package kz.amihady.exonix.organization.persistense.mapper;


import kz.amihady.exonix.organization.domain.Organization;
import kz.amihady.exonix.organization.persistense.entity.OrganizationEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrganizationMapper {

    public Organization toDomain(OrganizationEntity organizationEntity){
        return Organization.builder()
                .id(organizationEntity.getId())
                .email(organizationEntity.getEmail())
                .phone(organizationEntity.getPhone())
                .name(organizationEntity.getName())
                .build();
    }

    public OrganizationEntity toEntity(Organization organization){
        return OrganizationEntity.builder()
                .id(organization.getId())
                .email(organization.getEmail())
                .phone(organization.getPhone())
                .name(organization.getName())
                .build();
    }
}
