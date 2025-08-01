package com.quangnv.msb.service.impl;

import com.quangnv.msb.core.dto.auth.AuthRequestDTO;
import com.quangnv.msb.core.dto.auth.LoginType;
import com.quangnv.msb.core.dto.paging.PaginationRequest;
import com.quangnv.msb.core.dto.paging.PaginationResponse;
import com.quangnv.msb.core.exception.MAErrorCode;
import com.quangnv.msb.core.exception.UnExpectedException;
import com.quangnv.msb.core.funtional.Maybe;
import com.quangnv.msb.entity.User;
import com.quangnv.msb.repository.impl.UserRepositoryImpl;
import com.quangnv.msb.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<Long, User, UserRepositoryImpl> implements UserService {
    private final AuthenticationManager authenticationManager;

    protected UserServiceImpl(
            UserRepositoryImpl repository,
            AuthenticationManager authenticationManager
    ) {
        super(repository);
        this.authenticationManager = authenticationManager;
    }

    @Override
    public PaginationResponse<User> findUserByPagination(PaginationRequest paginationRequest) {
        return repository.findUserByPagination(paginationRequest);
    }

    @Override
    public User findUserByUsername(String username) {
        return Maybe.of(username)
                .map(repository::findUserByUsername)
                .orElseNull();
    }

    @Override
    public boolean isAuthenticated(User user, AuthRequestDTO payload) {
        if (user == null) {
            throw new UnExpectedException(MAErrorCode.USER_DOES_NOT_EXIST);
        }
        String username = payload.getUsername();
        LoginType loginType = payload.getLoginType();
        if (LoginType.FACE_ID == loginType || LoginType.FINGER_PRINT == loginType) {
            return true;
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, payload.getPassword());
        return authenticationManager.authenticate(authenticationToken).isAuthenticated();
    }
}