package com.quangnv.msb.facade;

import com.quangnv.msb.core.dto.auth.AuthRequestDTO;
import com.quangnv.msb.core.dto.auth.AuthResponseDTO;
import com.quangnv.msb.core.dto.paging.PaginationRequest;
import com.quangnv.msb.core.dto.paging.PaginationResponse;
import com.quangnv.msb.entity.User;

public interface UserFacade extends BaseFacade<Long, User> {
    PaginationResponse<User> findUserByPagination(PaginationRequest paginationRequest);
    AuthResponseDTO authenticate(AuthRequestDTO payload);
}