package kz.amihady.organization.service;

import kz.amihady.organization.api.mapper.OrganizationMapper;
import kz.amihady.organization.api.request.OrganizationCreateRequest;
import kz.amihady.organization.api.request.OrganizationUpdateRequest;
import kz.amihady.organization.api.response.OrganizationResponse;
import kz.amihady.organization.exception.OrganizationNotFoundException;
import kz.amihady.organization.model.Organization;
import kz.amihady.organization.repository.OrganizationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import static kz.amihady.organization.service.UpdateHelper.updateIfNotNull;


import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrganizationService {
    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MessageSource messageSource;

    public OrganizationResponse findById(UUID organizationId, Locale locale) {
        var organization = getOrganizationById(organizationId,locale);

        return organizationMapper.fromOrganization(organization);
    }

    public List<OrganizationResponse> findAll() {
        return organizationRepository
                .findAll()
                .stream()
                .map(organizationMapper::fromOrganization)
                .collect(Collectors.toList());
    }

    public OrganizationResponse create(OrganizationCreateRequest createRequest, Locale locale) {
        var organization = organizationMapper.toOrganization(createRequest);

        organizationRepository.save(organization);

        log.info(
                messageSource.getMessage(
                        "organization.created",
                        new Object[]{organization.getId()},
                        locale
                )
        );

        return organizationMapper.fromOrganization(organization);
    }

    public OrganizationResponse update(
            UUID organizationId,
            OrganizationUpdateRequest updateRequest,
            Locale locale)
    {
        var organization = getOrganizationById(organizationId,locale);

        updateIfNotNull(updateRequest.name(), organization::setName);
        updateIfNotNull(updateRequest.email(), organization::setEmail);
        updateIfNotNull(updateRequest.phone(),organization::setPhone);

        organizationRepository.save(organization);

        log.info(
                messageSource.getMessage(
                        "organization.updated",
                        new Object[]{organizationId},
                        locale
                )
        );

        return organizationMapper.fromOrganization(organization);
    }

    public void delete(
            UUID organizationId,
            Locale locale
    ){
        var organization = getOrganizationById(organizationId,locale);
        organizationRepository.deleteById(organizationId);

        log.info(
                messageSource.getMessage(
                        "organization.deleted",
                        new Object[]{organizationId},
                        locale
                )
        );


    }


    private Organization getOrganizationById(
            UUID organizationId,
            Locale locale)
    {
        return organizationRepository.findById(organizationId)
                .orElseThrow(()->
                        new OrganizationNotFoundException(
                                messageSource.getMessage(
                                        "organization.notFound",
                                        new Object[]{organizationId},
                                        locale
                                )
                        )
                );
    }
}
