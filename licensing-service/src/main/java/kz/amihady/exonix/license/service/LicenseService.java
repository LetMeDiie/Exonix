package kz.amihady.exonix.license.service;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import kz.amihady.exonix.license.api.mapper.LicenseApiMapper;
import kz.amihady.exonix.license.api.dto.request.LicenseCreateRequest;
import kz.amihady.exonix.license.api.dto.request.LicenseUpdateRequest;
import kz.amihady.exonix.license.api.dto.response.LicenseResponse;
import kz.amihady.exonix.license.handler.exception.LicenseNotFoundException;
import kz.amihady.exonix.license.domain.model.License;
import kz.amihady.exonix.license.persistence.adapter.LicensePersistenceAdapter;
import kz.amihady.exonix.organization.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import static kz.amihady.exonix.license.service.UpdateHelper.updateIfNotNull;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@Slf4j
public class LicenseService {

    @Autowired
    private LicensePersistenceAdapter licensePersistenceAdapter;
    @Autowired
    private MessageSource messages;

    @Autowired
    private LicenseApiMapper licenseApiMapper;

    @Autowired
    private OrganizationService organizationService;



    @RateLimiter(name = "licenseServiceRateLimiter")
    public LicenseResponse getLicense(
            UUID licenseId,
            UUID organizationId,
            Locale locale
    ) {
        var license =
                getLicenseByOrganizationAndLicenseId(organizationId,licenseId,locale);
        return licenseApiMapper.fromLicense(license);
    }


    @RateLimiter(name = "licenseServiceRateLimiter")
    public List<LicenseResponse> getLicensesByOrganization(UUID organizationId, Locale locale){
        var licenses =
                licensePersistenceAdapter
                        .findLicensesByOrganization(organizationId);

        return licenses.stream()
                        .map(licenseApiMapper::fromLicense)
                        .toList();
    }



    @RateLimiter(name = "licenseServiceCreateRateLimiter")
    public LicenseResponse createLicense(
            UUID organizationId,
            LicenseCreateRequest request,
            Locale locale
    ) {
        var organization =
                organizationService.
                        getOrganizationInfo(organizationId, locale);
        var license =
                licenseApiMapper.
                        toLicense(request, organization.id(), UUID.randomUUID());

        licensePersistenceAdapter
                .saveLicense(license);

        log.info(messages.getMessage(
                "license.created.message",
                new Object[]{license.getLicenseId()},
                locale
        ));

        return licenseApiMapper
                .fromLicense(license);
    }


    @RateLimiter(name = "licenseServiceRateLimiter")
    public LicenseResponse updateLicense(
            UUID licenseId,
            UUID organizationId,
            LicenseUpdateRequest request,
            Locale locale
    ) {
        var license = getLicenseByOrganizationAndLicenseId(
                organizationId,licenseId,locale);

        updateIfNotNull(request.productName(),license::setProductName);
        updateIfNotNull(request.description(),license::setDescription);
        updateIfNotNull(request.licenseType(),license::setLicenseType);

        licensePersistenceAdapter.
                saveLicense(license);

        log.info(messages.getMessage(
                "license.updated.message",
                new Object[]{license.getLicenseId()},
                locale
        ));

        return licenseApiMapper.fromLicense(license);
    }


    @RateLimiter(name = "licenseServiceRateLimiter")
    public void deleteLicense(
            UUID organizationId,
            UUID licenseId,
            Locale locale
    ) {
        var license =
                getLicenseByOrganizationAndLicenseId(organizationId,licenseId,locale);

        licensePersistenceAdapter.deleteLicense(license);

        log.info(messages.getMessage(
                "license.deleted.message",
                new Object[]{license.getLicenseId()},
                locale
        ));
    }



    private License getLicenseByOrganizationAndLicenseId(
            UUID organizationId,
            UUID licenseId,
            Locale locale
    ) {
        var license = licensePersistenceAdapter
                .getLicense(organizationId, licenseId);

        if (license.isEmpty()) {
            var errorMessage = messages.getMessage(
                    "license.search.error.message",
                    new Object[]{licenseId, organizationId},
                    locale
            );
            log.error(errorMessage);
            throw new LicenseNotFoundException(errorMessage);
        }

        return license.get();
    }


}