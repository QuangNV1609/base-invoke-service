package com.quangnv.msb.repository.impl;

import com.quangnv.msb.core.dto.paging.Pagination;
import com.quangnv.msb.core.dto.paging.Sort;
import com.quangnv.msb.entity.User;
import com.quangnv.msb.entity.QUser;
import com.quangnv.msb.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl extends BaseRepositoryImpl<Long, User> implements UserRepository {
    public UserRepositoryImpl(EntityManager em) {
        super(User.class, em);
    }

    @Override
    public List<User> findUserByPagination(Pagination pagination) {
        QUser qUser = QUser.user;
        BooleanBuilder builder = new BooleanBuilder();

        //Filters.
        pagination.getFilters().forEach(item -> {
            switch (item.getColumn()) {
                case "name":
                    builder.and(qUser.name.containsIgnoreCase(item.getValue()));
                    break;
                default:
                    break;
            }
        });

        //Sorts.
        List<OrderSpecifier<?>> sortOrders = new ArrayList<>();
        pagination.getSorts().forEach(sort -> {
            switch (sort.getColumn()) {
                case "created_at":
                    sortOrders.add(sort.getOrder() == Sort.EnumSort.ASC ?
                            qUser.createdAt.asc() : qUser.createdAt.desc());
                    break;
                default:
                    break;
            }
        });

        // Pagination: page = 0-based
        return queryFactory.selectFrom(qUser)
                .where(builder)
                .orderBy(sortOrders.toArray(new OrderSpecifier[0]))
                .offset((pagination.getPage() - 1) * pagination.getLimit())
                .limit(pagination.getLimit())
                .fetch();
    }
}
