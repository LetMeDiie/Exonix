package kz.amihady.exonix.organization.config;

import feign.FeignException;
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import kz.amihady.exonix.organization.handler.exception.OrganizationNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class OrgCircuitBreakerConfig {
    @Bean
    public CircuitBreakerConfigCustomizer organizationServiceCircuitBreakerCustomizer() {
        return CircuitBreakerConfigCustomizer.of(
                "organizationServiceCircuitBreaker", builder -> builder
                        .recordExceptions(
                                FeignException.ServiceUnavailable.class,
                                FeignException.InternalServerError.class,
                                FeignException.BadGateway.class,
                                FeignException.GatewayTimeout.class,
                                FeignException.class,
                                TimeoutException.class,
                                IOException.class
                        )
                        .ignoreExceptions(
                                OrganizationNotFoundException.class
                        )
        );
    }
}
