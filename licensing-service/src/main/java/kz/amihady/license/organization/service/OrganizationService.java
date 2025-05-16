package kz.amihady.license.organization.service;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import kz.amihady.license.exception.OrganizationServiceException;
import kz.amihady.license.organization.client.OrganizationFeignClient;
import kz.amihady.license.organization.response.OrganizationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.UUID;

@Service
@Slf4j
public class OrganizationService {

    @Autowired
    private OrganizationFeignClient organizationFeignClient;

    @Autowired
    MessageSource messageSource;


    @CircuitBreaker(name = "organizationService")
    public OrganizationResponse getLicenseInfo(UUID organizationId, Locale locale) {
        try {
            return organizationFeignClient.getOrganizationById(
                    organizationId, locale.getLanguage());
        } catch (FeignException.NotFound ex) {
            String errorMessage = messageSource.getMessage(
                    "organization.search.error.message",
                    new Object[]{organizationId},
                    locale
            );
            throw new OrganizationServiceException(errorMessage, HttpStatus.NOT_FOUND);
        } catch (FeignException.InternalServerError ex) {
            String errorMessage = messageSource.getMessage(
                    "organization.internal.server.error",
                    new Object[]{organizationId},
                    locale
            );
            throw new OrganizationServiceException(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (FeignException ex) {
            String errorMessage = messageSource.getMessage(
                    "organization.generic.error",
                    new Object[]{organizationId},
                    locale
            );
            throw new OrganizationServiceException(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }
}
