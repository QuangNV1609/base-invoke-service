package com.quangnv.msb.configuration.security.audit;

import com.quangnv.msb.configuration.security.dto.AuthUser;
import com.quangnv.msb.configuration.security.utils.SecurityContextUtils;
import com.quangnv.msb.core.funtional.Maybe;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityAuditorAware implements AuditorAware<String> {
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        return Maybe.of(SecurityContextUtils::getActualUser)
                .castValueTo(AuthUser.class)
                .map(AuthUser::getUsername)
                .map(Optional::of)
                .orElse(Optional.of("anonymous"));
    }
}
