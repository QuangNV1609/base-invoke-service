package com.quangnv.msb.configuration.security;

import com.quangnv.msb.configuration.enums.UserStatus;
import com.quangnv.msb.configuration.security.dto.AuthUser;
import com.quangnv.msb.core.exception.MAErrorCode;
import com.quangnv.msb.core.exception.UnExpectedException;
import com.quangnv.msb.entity.User;
import com.quangnv.msb.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserFetch(username);
        return this.toAuthUser(user);
    }

    public AuthUser toAuthUser(User user) {
        if (user == null) {
            throw new UnExpectedException(MAErrorCode.USER_DOES_NOT_EXIST);
        }

        boolean isActive = UserStatus.ACTIVE == user.getStatus();
        return AuthUser.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .phone(user.getPhone())
                .fullName(user.getFullName())
                .accountNonLocked(!(UserStatus.LOCK == user.getStatus()))
                .enabled(isActive)
                .accountNonExpired(isActive)
                .credentialsNonExpired(isActive)
                .role(user.getRole())
                .build();
    }
}
