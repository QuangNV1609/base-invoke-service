package com.quangnv.msb.repository;

import com.quangnv.msb.core.dto.paging.Pagination;
import com.quangnv.msb.entity.User;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface UserRepository extends BaseRepository<Long, User> {
    List<User> findUserByPagination(Pagination pagination);
}