package com.quangnv.msb.service;

import com.quangnv.msb.core.dto.paging.Pagination;
import com.quangnv.msb.entity.User;

import java.util.List;

public interface UserService extends BaseService<Long, User> {
    List<User> findUserByPagination(Pagination pagination);
}