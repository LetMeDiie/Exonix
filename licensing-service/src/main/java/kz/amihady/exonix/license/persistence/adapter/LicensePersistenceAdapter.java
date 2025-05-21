package kz.amihady.exonix.license.persistence.adapter;


import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import kz.amihady.exonix.license.domain.model.License;
import kz.amihady.exonix.license.persistence.mapper.LicenseMapper;
import kz.amihady.exonix.license.persistence.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class LicensePersistenceAdapter {
    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private LicenseMapper licenseMapper;


    @CircuitBreaker(name = "licenseDbCircuitBreaker")
    @Retry(name = "licenseDbRetry")
    public void saveLicense (License license){
        var licenseEntity =
                licenseMapper.toEntity(license);

        licenseRepository.save(licenseEntity);
    }

    @CircuitBreaker(name = "licenseDbCircuitBreaker")
    @Retry(name = "licenseDbRetry")
    public Optional<License> getLicense(UUID organizationId, UUID licenseId){
        var licenseEntity =
                licenseRepository
                        .findByOrganizationIdAndLicenseId(organizationId,licenseId);

        return licenseEntity.map(
                entity -> licenseMapper.toDomain(entity));

    }

    @CircuitBreaker(name = "licenseDbCircuitBreaker")
    @Retry(name = "licenseDbRetry")
    public void deleteLicense(License license){
        var licenseEntity =
                licenseMapper.toEntity(license);

        licenseRepository.delete(licenseEntity);
    }

    @CircuitBreaker(name = "licenseDbCircuitBreaker")
    @Retry(name = "licenseDbRetry")
    public List<License> findLicensesByOrganization(UUID organizationId){
        return licenseRepository.findByOrganizationId(organizationId).stream()
                .map(licenseMapper::toDomain)
                .toList();
    }

}
