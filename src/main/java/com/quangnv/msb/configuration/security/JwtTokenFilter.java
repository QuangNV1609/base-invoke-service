package com.quangnv.msb.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quangnv.msb.configuration.api.ApiAction;
import com.quangnv.msb.configuration.security.dto.AuthUser;
import com.quangnv.msb.configuration.web.MDCContextHolder;
import com.quangnv.msb.configuration.web.MDCKey;
import com.quangnv.msb.core.body.ApplicationRequestBody;
import com.quangnv.msb.core.funtional.Maybe;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    public JwtTokenFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {
        String bearerToken = tokenProvider.extractToken(request);
        if (tokenProvider.isValidAccessToken(bearerToken)) {
            Maybe.of(bearerToken)
                    .map(tokenProvider::getAuthentication)
                    .peek(SecurityContextHolder.getContext()::setAuthentication)
                    .map(Authentication::getPrincipal)
                    .castValueTo(AuthUser.class)
                    .peek(user -> MDCContextHolder.set(MDCKey.LOGIN, String.join("-", user.getUsername(), user.getPhone())));
        }
        filterChain.doFilter(request, response);
    }

    private ApiAction extractActionFromRequest(HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();

        return Maybe.of(request::getInputStream)
                .mapThrowingFunc(inputStream -> mapper.readValue(inputStream, ApplicationRequestBody.class))
                .map(ApplicationRequestBody::getAction)
                .orElseNull();
    }
}
