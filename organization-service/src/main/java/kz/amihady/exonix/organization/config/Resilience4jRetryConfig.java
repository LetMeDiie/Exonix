package kz.amihady.exonix.organization.config;

import io.github.resilience4j.common.retry.configuration.RetryConfigCustomizer;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.LockTimeoutException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.TransientDataAccessException;

import java.sql.SQLException;

@Configuration
public class Resilience4jRetryConfig {

    @Bean
    public RetryConfigCustomizer retryLicenseServiceCustomizer() {
        return RetryConfigCustomizer
                .of("organizationDbRetry", builder -> builder
                        .retryExceptions(
                                JDBCConnectionException.class,
                                LockTimeoutException.class,
                                SQLException.class,
                                TransientDataAccessException.class
                        )
                        .ignoreExceptions(
                                IllegalArgumentException.class
                        )
                );
    }

}
