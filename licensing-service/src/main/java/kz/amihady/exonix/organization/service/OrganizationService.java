package kz.amihady.exonix.organization.service;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import kz.amihady.exonix.organization.handler.exception.OrganizationServiceException;
import kz.amihady.exonix.organization.client.OrganizationFeignClient;
import kz.amihady.exonix.organization.handler.exception.OrganizationNotFoundException;
import kz.amihady.exonix.organization.dto.Organization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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


    @CircuitBreaker(name = "organizationServiceCircuitBreaker")
    @Retry(name = "organizationServiceRetry")
    public Organization getOrganizationInfo(UUID organizationId, Locale locale) {
        try {
            return organizationFeignClient.getOrganizationById(organizationId, locale.getLanguage());
        } catch (FeignException.NotFound ex) {
            String errorMessage = messageSource.getMessage(
                    "organization.search.error.message",
                    new Object[]{organizationId},
                    locale);
            throw new OrganizationNotFoundException(errorMessage);
        }
    }

    public Organization organizationFallback(
            UUID organizationId,
            Locale locale,
            Throwable throwable
    ) {
        String errorMessage = messageSource.getMessage(
                "organization.fallback.error",
                null,
                locale
        );
        throw new OrganizationServiceException(errorMessage);
    }

}
