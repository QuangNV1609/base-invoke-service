package com.quangnv.msb.facade;

import com.quangnv.msb.core.dto.paging.Pagination;
import com.quangnv.msb.entity.User;

import java.util.List;

public interface UserFacade extends BaseFacade<Long, User> {
    List<User> findUserByPagination(Pagination pagination);
}