package kz.amihady.gatewayserver.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.http.HttpHeaders;


@Order(1)
@Component
@Slf4j
public class TrackingFilter implements GlobalFilter {

    @Autowired
    private FilterUtils filterUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var requestHeaders =
                exchange.getRequest().getHeaders();


        if (isCorrelationIdPresent(requestHeaders)) {
            log.info("tmx-correlation-id found in tracking filter: {}. ",
                    filterUtils.getCorrelationId(requestHeaders));
        }
        else {
            String correlationID = generateCorrelationId();
            exchange = filterUtils.setCorrelationId(exchange,correlationID);
            log.info("tmx-correlation-id generated in tracking filter: {}.", correlationID);
        }
        return chain.filter(exchange);
    }


    private boolean isCorrelationIdPresent(HttpHeaders requestHeaders) {
        return filterUtils.getCorrelationId(requestHeaders) != null;
    }

    private String generateCorrelationId() {
        return java.util.UUID.randomUUID().toString();
    }
}

