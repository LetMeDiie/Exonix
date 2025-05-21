package kz.amihady.exonix.organization.config;

import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.exception.LockTimeoutException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.dao.TransientDataAccessException;

import java.sql.SQLRecoverableException;
import java.sql.SQLTransientConnectionException;

@Configuration
public class Resilience4jCircuitBreakerConfig {
    @Bean
    public CircuitBreakerConfigCustomizer licenseServiceCircuitBreakerCustomizer() {
        return CircuitBreakerConfigCustomizer.of(
                "organizationDbCircuitBreaker", builder -> builder
                        .recordExceptions(
                                JDBCConnectionException.class,
                                LockTimeoutException.class,
                                QueryTimeoutException.class,
                                SQLTransientConnectionException.class,
                                SQLRecoverableException.class,
                                TransientDataAccessException.class,
                                CannotAcquireLockException.class,
                                DataAccessResourceFailureException.class
                        )
        );
    }
}
