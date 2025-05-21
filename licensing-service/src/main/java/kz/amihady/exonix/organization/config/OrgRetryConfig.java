package kz.amihady.exonix.organization.config;

import feign.FeignException;
import io.github.resilience4j.common.retry.configuration.RetryConfigCustomizer;
import kz.amihady.exonix.organization.handler.exception.OrganizationNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeoutException;

@Configuration
public class OrgRetryConfig {

    @Bean
    public RetryConfigCustomizer retryOrganizationServiceCustomizer() {
        return RetryConfigCustomizer
                .of("organizationServiceRetry", builder -> builder
                        .retryExceptions(
                                FeignException.ServiceUnavailable.class,
                                FeignException.GatewayTimeout.class,
                                FeignException.InternalServerError.class,
                                TimeoutException.class
                        )
                        .ignoreExceptions(
                                OrganizationNotFoundException.class
                        )
                );
    }

}
