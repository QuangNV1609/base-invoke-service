package com.quangnv.msb.service;

import com.quangnv.msb.core.dto.auth.AuthRequestDTO;
import com.quangnv.msb.core.dto.paging.PaginationRequest;
import com.quangnv.msb.core.dto.paging.PaginationResponse;
import com.quangnv.msb.entity.User;

public interface UserService extends BaseService<Long, User> {
    PaginationResponse<User> findUserByPagination(PaginationRequest paginationRequest);
    User findUserByUsername(String username);
    boolean isAuthenticated(User user, AuthRequestDTO payload);
}