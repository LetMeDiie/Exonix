package kz.amihady.license.license.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import kz.amihady.license.license.api.mapper.LicenseMapper;
import kz.amihady.license.license.api.request.LicenseCreateRequest;
import kz.amihady.license.license.api.request.LicenseUpdateRequest;
import kz.amihady.license.license.api.response.LicenseResponse;
import kz.amihady.license.exception.LicenseNotFoundException;
import kz.amihady.license.license.model.License;
import kz.amihady.license.license.repository.LicenseRepository;
import kz.amihady.license.organization.service.OrganizationService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.tool.schema.spi.SqlScriptException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import static kz.amihady.license.license.service.UpdateHelper.updateIfNotNull;


import java.util.List;
import java.util.Locale;
import java.util.UUID;


@Service
@Slf4j
public class LicenseService {
    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private MessageSource messages;

    @Autowired
    private LicenseMapper licenseMapper;

    @Autowired
    private OrganizationService organizationService;



    public LicenseResponse getLicense(
            UUID licenseId,
            UUID organizationId,
            Locale locale
    ){
        var license =
                getLicenseByOrganizationAndLicenseId(organizationId,licenseId,locale);

        return licenseMapper.fromLicense(license);
    }

    @CircuitBreaker(name = "licenseService")
    public List<LicenseResponse> getLicensesByOrganization(
            UUID organizationId,
            Locale locale
    ){

        var licenses =
                licenseRepository
                        .findByOrganizationId(organizationId);



        var licensesResponses =
                licenses.stream()
                        .map(licenseMapper::fromLicense)
                        .toList();

        return licensesResponses;
    }


    public LicenseResponse createLicense(
            UUID organizationId,
            LicenseCreateRequest request,
            Locale locale
    ){
        var organization =
                organizationService
                        .getLicenseInfo(organizationId,locale);

        var license =
                licenseMapper.
                        toLicense(request,organization.id());

        licenseRepository.save(license);

        log.info(messages.getMessage(
                "license.created.message",
                new Object[]{license.getLicenseId()},
                locale
        ));

        return licenseMapper.fromLicense(license);
    }

    public LicenseResponse updateLicense(
            UUID licenseId,
            UUID organizationId,
            LicenseUpdateRequest request,
            Locale locale
    ){
        var license = getLicenseByOrganizationAndLicenseId(
                organizationId,licenseId,locale);

        updateIfNotNull(request.productName(),license::setProductName);
        updateIfNotNull(request.description(),license::setDescription);
        updateIfNotNull(request.licenseType(),license::setLicenseType);

        licenseRepository.save(license);

        log.info(messages.getMessage(
                "license.updated.message",
                new Object[]{license.getLicenseId()},
                locale
        ));

        return licenseMapper.fromLicense(license);
    }

    public void deleteLicense(
            UUID organizationId,
            UUID licenseId,
            Locale locale
    ){
        var license =
                getLicenseByOrganizationAndLicenseId(organizationId,licenseId,locale);

        licenseRepository.delete(license);

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
    ){
        return licenseRepository
                .findByOrganizationIdAndLicenseId(organizationId, licenseId)
                .orElseThrow(() -> new LicenseNotFoundException(
                        messages.getMessage("license.search.error.message",
                                new Object[]{licenseId, organizationId}, locale)
                ));
    }
}