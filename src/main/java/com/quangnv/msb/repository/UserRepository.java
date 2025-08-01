package com.quangnv.msb.repository;

import com.quangnv.msb.core.dto.paging.PaginationRequest;
import com.quangnv.msb.core.dto.paging.PaginationResponse;
import com.quangnv.msb.entity.User;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserRepository extends BaseRepository<Long, User> {
    PaginationResponse<User> findUserByPagination(PaginationRequest paginationRequest);
    User findUserByUsername(String username);
    User findUserFetch(String loginId);
}