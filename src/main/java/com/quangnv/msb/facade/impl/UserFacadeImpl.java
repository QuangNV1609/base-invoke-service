package com.quangnv.msb.facade.impl;

import com.quangnv.msb.configuration.api.ApiAction;
import com.quangnv.msb.configuration.api.MetaFlow;
import com.quangnv.msb.core.dto.paging.Pagination;
import com.quangnv.msb.entity.User;
import com.quangnv.msb.facade.Facade;
import com.quangnv.msb.facade.UserFacade;
import com.quangnv.msb.service.UserService;

import java.util.List;

@Facade
public class UserFacadeImpl extends BaseFacadeImpl<Long, User, UserService> implements UserFacade {
    protected UserFacadeImpl(UserService service) {
        super(service);
    }

    @Override
    @MetaFlow(action = ApiAction.FIND_USER_BY_FILTER)
    public List<User> findUserByPagination(Pagination pagination) {
        return service.findUserByPagination(pagination);
    }
}