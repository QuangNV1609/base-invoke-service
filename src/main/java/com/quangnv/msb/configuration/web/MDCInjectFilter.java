package com.quangnv.msb.configuration.web;

import com.quangnv.msb.configuration.token.TokenKeyIssuer;
import com.quangnv.msb.core.funtional.Maybe;
import com.quangnv.msb.core.utils.RequestHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MDCInjectFilter extends OncePerRequestFilter {

    private final TokenKeyIssuer tokenIssuer;

    public MDCInjectFilter(@Qualifier("UUIDTokenKeyIssuer") TokenKeyIssuer tokenIssuer) {
        this.tokenIssuer = tokenIssuer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String token = Maybe.of(() -> request.getHeader("X-TOKEN"))
                    .orElse(tokenIssuer::issue);
            MDCContextHolder.setContext(MDCKey.TK, token);
            MDCContextHolder.setContext(MDCKey.RT, String.valueOf(Instant.now().toEpochMilli()));
            MDCContextHolder.setContext(MDCKey.CLIENT_IP, RequestHelper.getClientIpAddress(request));
            if (log.isInfoEnabled()) {
                log.info(">>>Clean context reset START");
            }
            chain.doFilter(request, response);
        } finally {
            Arrays.stream(MDCKey.values())
                    .map(MDCKey::name)
                    .collect(Collectors.toList())
                    .forEach(MDCContextHolder::remove);
            if (log.isInfoEnabled()) {
                log.info(">>> Clean context reset END");
            }
        }
    }
}
