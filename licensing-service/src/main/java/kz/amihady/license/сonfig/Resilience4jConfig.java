package kz.amihady.license.сonfig;

import feign.FeignException;
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import kz.amihady.license.exception.OrganizationServiceException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Configuration
public class Resilience4jConfig {

    @Bean
    public CircuitBreakerConfigCustomizer licenseServiceCircuitBreakerCustomizer() {
        return CircuitBreakerConfigCustomizer.of(
                "licenseService", builder -> builder
                    .recordExceptions(
                            HttpServerErrorException.class,
                            IOException.class,
                            TimeoutException.class,
                            ResourceAccessException.class
                    )
        );
    }

    @Bean
    public CircuitBreakerConfigCustomizer organizationServiceCircuitBreakerCustomizer() {
        return CircuitBreakerConfigCustomizer.of(
                "organizationService", builder -> builder
                        .recordExceptions(
                                OrganizationServiceException.class
                        )
        );
    }
}
