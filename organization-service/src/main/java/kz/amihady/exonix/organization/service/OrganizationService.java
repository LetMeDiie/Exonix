package kz.amihady.exonix.organization.service;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import kz.amihady.exonix.organization.api.mapper.OrganizationApiMapper;
import kz.amihady.exonix.organization.api.dto.request.OrganizationCreateRequest;
import kz.amihady.exonix.organization.api.dto.request.OrganizationUpdateRequest;
import kz.amihady.exonix.organization.api.dto.response.OrganizationResponse;
import kz.amihady.exonix.organization.handler.exception.OrganizationNotFoundException;
import kz.amihady.exonix.organization.domain.Organization;
import kz.amihady.exonix.organization.persistense.adapter.OrganizationPersistenceAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import static kz.amihady.exonix.organization.service.UpdateHelper.updateIfNotNull;


import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@Slf4j
public class OrganizationService {
    @Autowired
    private OrganizationApiMapper organizationApiMapper;

    @Autowired
    private OrganizationPersistenceAdapter organizationPersistenceAdapter;
    @Autowired
    private MessageSource messageSource;


    @RateLimiter(name = "organizationServiceRateLimiter")
    public OrganizationResponse findById(UUID organizationId, Locale locale) {
        var organization = getOrganizationById(organizationId, locale);

        return organizationApiMapper.fromOrganization(organization);
    }

    public List<OrganizationResponse> findAll() {
        var organizations =
                organizationPersistenceAdapter
                        .findAll();

        return organizations.stream()
                .map(organizationApiMapper::fromOrganization)
                .toList();
    }

    @RateLimiter(name = "organizationServiceRateLimiter")
    public OrganizationResponse create(OrganizationCreateRequest createRequest, Locale locale) {
        var organization =
                organizationApiMapper.
                        toOrganization(createRequest,UUID.randomUUID());

        organizationPersistenceAdapter
                        .save(organization);

        log.info(
                messageSource.getMessage(
                        "organization.created",
                        new Object[]{organization.getId()},
                        locale
                )
        );

        return organizationApiMapper.fromOrganization(organization);
    }

    @RateLimiter(name = "organizationServiceRateLimiter")
    public OrganizationResponse update(
            UUID organizationId,
            OrganizationUpdateRequest updateRequest,
            Locale locale
    ) {
        var organization =
                getOrganizationById(organizationId, locale);

        updateIfNotNull(updateRequest.name(), organization::setName);
        updateIfNotNull(updateRequest.email(), organization::setEmail);
        updateIfNotNull(updateRequest.phone(), organization::setPhone);

        organizationPersistenceAdapter
                .save(organization);

        log.info(
                messageSource.getMessage(
                        "organization.updated",
                        new Object[]{organizationId},
                        locale
                )
        );

        return organizationApiMapper.fromOrganization(organization);
    }

    @RateLimiter(name = "organizationServiceRateLimiter")
    public void delete(UUID organizationId, Locale locale) {
        var organization =
                getOrganizationById(organizationId, locale);

        organizationPersistenceAdapter
                .delete(organization);

        log.info(
                messageSource.getMessage(
                        "organization.deleted",
                        new Object[]{organizationId},
                        locale
                )
        );


    }


    private Organization getOrganizationById(UUID organizationId, Locale locale) {
        var organization =
                organizationPersistenceAdapter
                        .findById(organizationId);

        if (organization.isEmpty()) {
            var errorMessage =
                    messageSource.getMessage(
                            "organization.notFound",
                            new Object[]{organizationId},
                            locale
                    );

            log.error(errorMessage);
            throw new OrganizationNotFoundException(errorMessage);
        }

        return organization.get();
    }
}