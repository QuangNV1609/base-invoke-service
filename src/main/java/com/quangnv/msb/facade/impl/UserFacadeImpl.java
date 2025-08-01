package com.quangnv.msb.facade.impl;

import com.quangnv.msb.configuration.api.ApiAction;
import com.quangnv.msb.configuration.api.MetaFlow;
import com.quangnv.msb.configuration.security.CustomUserDetailService;
import com.quangnv.msb.configuration.security.TokenProvider;
import com.quangnv.msb.configuration.security.dto.AuthUser;
import com.quangnv.msb.core.dto.auth.AuthRequestDTO;
import com.quangnv.msb.core.dto.auth.AuthResponseDTO;
import com.quangnv.msb.core.dto.paging.PaginationRequest;
import com.quangnv.msb.core.dto.paging.PaginationResponse;
import com.quangnv.msb.core.exception.MAErrorCode;
import com.quangnv.msb.core.token.TokenDTO;
import com.quangnv.msb.entity.User;
import com.quangnv.msb.facade.Facade;
import com.quangnv.msb.facade.UserFacade;
import com.quangnv.msb.service.UserService;
import com.quangnv.msb.utils.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.transaction.annotation.Transactional;

@Facade
public class UserFacadeImpl extends BaseFacadeImpl<Long, User, UserService> implements UserFacade {
    private final CustomUserDetailService customUserDetailService;
    private final TokenProvider tokenProvider;

    protected UserFacadeImpl(
            UserService service,
            CustomUserDetailService customUserDetailService,
            TokenProvider tokenProvider
    ) {
        super(service);
        this.customUserDetailService = customUserDetailService;
        this.tokenProvider = tokenProvider;
    }

    @Override
    @MetaFlow(action = ApiAction.FIND_USER_BY_FILTER)
    public PaginationResponse<User> findUserByPagination(PaginationRequest paginationRequest) {
        return service.findUserByPagination(paginationRequest);
    }

    @Override
    @Transactional
    @MetaFlow(action = ApiAction.LOGIN)
    public AuthResponseDTO authenticate(AuthRequestDTO payload) {
        User user = service.findUserByUsername(payload.getUsername());
        boolean authenticated = service.isAuthenticated(user, payload);
        if (!authenticated) {
            throw new AuthenticationServiceException(MAErrorCode.AUTH_FAIL);
        }

        AuthUser authUser = customUserDetailService.toAuthUser(user);
        TokenDTO accessToken = tokenProvider.createAccessToken(authUser);
        return AuthResponseDTO.builder()
                .accessToken(accessToken)
                .role(authUser.getRole())
                .user(UserMapper.INSTANCE.fromEntityToAuthResponse(user))
                .build();
    }
}