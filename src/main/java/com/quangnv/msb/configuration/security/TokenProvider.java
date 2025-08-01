package com.quangnv.msb.configuration.security;

import com.quangnv.msb.configuration.security.dto.AuthUser;
import com.quangnv.msb.configuration.security.utils.SecurityContextUtils;
import com.quangnv.msb.core.funtional.Maybe;
import com.quangnv.msb.core.token.TokenDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    private static final String BEARER = "Bearer ";

    private final CustomUserDetailService customUserDetailService;
    private final SecurityProperties securityProperties;

    public String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && StringUtils.startsWithIgnoreCase(bearerToken, BEARER)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Authentication getAuthentication(String accessToken) {
        String username = getUsername(accessToken);
        AuthUser authUser = (AuthUser) customUserDetailService.loadUserByUsername(username);
        return SecurityContextUtils.getAuthentication(authUser);
    }

    public TokenDTO createAccessToken(AuthUser user) {
        String username = user.getUsername();
        Set<String> authorities = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        int accessExpirationTime = securityProperties.getJwt().getAccessTokenTtlInMinutes();
        String accessToken = createAccessToken(username, authorities, accessExpirationTime);
        return new TokenDTO(accessToken, accessExpirationTime);
    }

    public String createAccessToken(String username, Set<String> authorities, int timeout) {
        SecretKey key = Keys.hmacShaKeyFor(securityProperties.getJwt().getKey().getBytes());
        Date expiration = Date.from(Instant.now().plus(timeout, ChronoUnit.MINUTES));
        return Jwts.builder()
                .signWith(key)
                .issuer("secured-api")
                .subject(username)
                .expiration(expiration)
                .claim("authorities", authorities)
                .compact();
    }

    public String getUsername(String token) {
        return Maybe.of(token)
                .map(this::parseToken)
                .map(Claims::getSubject)
                .orElseNull();
    }

    public boolean isValidAccessToken(String token) {
        return isValidToken(token);
    }

    private boolean isValidToken(String token) {
        return Maybe.of(token)
                .mapThrowingFunc(this::parseToken)
                .isJust();
    }

    public Claims parseToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(securityProperties.getJwt().getKey().getBytes());
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
