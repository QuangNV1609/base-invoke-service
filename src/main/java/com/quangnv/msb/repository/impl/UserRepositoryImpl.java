package com.quangnv.msb.repository.impl;

import com.quangnv.msb.core.dto.paging.PaginationRequest;
import com.quangnv.msb.core.dto.paging.PaginationResponse;
import com.quangnv.msb.core.dto.paging.Sort;
import com.quangnv.msb.core.utils.PaginationUtils;
import com.quangnv.msb.entity.QUser;
import com.quangnv.msb.entity.User;
import com.quangnv.msb.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserRepositoryImpl extends BaseRepositoryImpl<Long, User> implements UserRepository {
    public UserRepositoryImpl(EntityManager em) {
        super(User.class, em);
    }

    @Override
    public PaginationResponse<User> findUserByPagination(PaginationRequest paginationRequest) {
        Pageable pageable = paginationRequest.getPageable();
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();

        //Filters.
        paginationRequest.getFilters().forEach(item -> {
            switch (item.getColumn()) {
                case "fullName":
                    builder.and(qUser.fullName.containsIgnoreCase(item.getValue()));
                    break;
                default:
                    break;
            }
        });

        //Sorts.
        List<OrderSpecifier<?>> sortOrders = new ArrayList<>();
        paginationRequest.getSorts().forEach(sort -> {
            switch (sort.getColumn()) {
                case "createdAt":
                    sortOrders.add(sort.getOrder() == Sort.EnumSort.ASC ?
                            qUser.createdAt.asc() : qUser.createdAt.desc());
                    break;
                default:
                    break;
            }
        });

        // Pagination: page = 0-based
        List<User> users = queryFactory.selectFrom(qUser)
                .where(builder)
                .orderBy(sortOrders.toArray(new OrderSpecifier[0]))
                .offset((paginationRequest.getPage() - 1) * paginationRequest.getLimit())
                .limit(paginationRequest.getLimit())
                .fetch();
        // Tổng số bản ghi (dùng cho phân trang)
        Long total = queryFactory.select(qUser.count())
                .from(qUser)
                .where(builder)
                .fetchOne();
        return PaginationUtils.buildPaginationResponse(PageableExecutionUtils.getPage(users, pageable, () -> total));
    }

    @Override
    public User findUserFetch(String loginId) {
        Objects.requireNonNull(loginId);
        String lwLoginId = loginId.toLowerCase();
        QUser qUser = QUser.user;

        BooleanExpression condOnUsername = qUser.username.eq(lwLoginId)
                .or(qUser.phone.eq(lwLoginId));
        return queryFactory.selectFrom(qUser)
                .where(condOnUsername)
                .fetchOne();
    }

    @Override
    public User findUserByUsername(String username) {
        QUser qUser = QUser.user;
        return queryFactory.selectFrom(qUser)
                .where(qUser.username.eq(username.toLowerCase()))
                .limit(1)
                .fetchOne();
    }
}
