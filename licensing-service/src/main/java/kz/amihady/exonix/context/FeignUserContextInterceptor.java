package kz.amihady.exonix.context;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static kz.amihady.exonix.context.UserContextHeaders.*;


@Component
@Slf4j
public class FeignUserContextInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        var context =
                UserContextHolder.getUserContext();

        log.info("Adding Correlation ID to Feign request: {}", context.getCorrelationId());
        requestTemplate.header(CORRELATION_ID,context.getCorrelationId());
    }
}
