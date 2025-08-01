package com.quangnv.msb.configuration.security.utils;

import com.quangnv.msb.configuration.security.dto.AuthUser;
import com.quangnv.msb.core.exception.MAErrorCode;
import com.quangnv.msb.core.exception.UnSatisfiedRuleException;
import com.quangnv.msb.core.funtional.Maybe;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@UtilityClass
public class SecurityContextUtils {

    private static final Logger log = LoggerFactory.getLogger(SecurityContextUtils.class);

    public static UserDetails getActualUser() {
        return Maybe.of(SecurityContextHolder::getContext)
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .castValueTo(UserDetails.class)
                .orElseThrow(new AuthenticationServiceException("Not Authorization!"));
    }

    public static AuthUser getUser() {
        return (AuthUser) getActualUser();
    }

    public static Long getUserId() {
        return getUser().getId();
    }

    public static boolean hasAuthority(String authority) {
        AuthUser user = getUser();
        return user.getAuthorities().stream().anyMatch(o -> o.getAuthority().equals(authority));
    }

    public static Authentication getAuthentication(AuthUser user) {
        return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
    }


    public static boolean isRole(String role) {
        AuthUser user = SecurityContextUtils.getUser();
        String userRole = user.getRole();
        return userRole.equals(role);
    }

    public static void requirePermission(Set<String> acceptRoles) {
        AuthUser user = SecurityContextUtils.getUser();
        String role = user.getRole();
        if (!acceptRoles.contains(role)) {
            log.info(">>>User {} does not have right access to perform this action", user.getUsername());
            throw new UnSatisfiedRuleException(MAErrorCode.ACCESS_DENIED);
        }
    }
}
