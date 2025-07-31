package com.quangnv.msb.service.impl;

import com.quangnv.msb.core.dto.paging.Pagination;
import com.quangnv.msb.entity.User;
import com.quangnv.msb.repository.impl.UserRepositoryImpl;
import com.quangnv.msb.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends BaseServiceImpl<Long, User, UserRepositoryImpl> implements UserService {
    protected UserServiceImpl(UserRepositoryImpl repository) {
        super(repository);
    }

    @Override
    public List<User> findUserByPagination(Pagination pagination) {
        return repository.findUserByPagination(pagination);
    }
}