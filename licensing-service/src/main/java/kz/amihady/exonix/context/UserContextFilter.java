package kz.amihady.exonix.context;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static kz.amihady.exonix.context.UserContextHeaders.*;

@Component
@Slf4j
public class UserContextFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest =
                (HttpServletRequest) servletRequest;

        var userContext = UserContextHolder.getUserContext();
        userContext.setCorrelationId(httpServletRequest.getHeader(CORRELATION_ID));

        log.info("UserContextFilter Correlation id: {}", UserContextHolder.getUserContext().getCorrelationId());

        filterChain.doFilter(httpServletRequest,servletResponse);
    }
}
