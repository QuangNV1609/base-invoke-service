package com.quangnv.msb.repository.impl;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.quangnv.msb.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslJpaPredicateExecutor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.io.Serializable;

public class BaseRepositoryImpl<I extends Serializable, T> extends SimpleJpaRepository<T, I> implements BaseRepository<I, T> {
    protected final EntityManager em;
    protected final JPAQueryFactory queryFactory;
    protected final QuerydslJpaPredicateExecutor<T> predicateExecutor;
    protected final Querydsl querydsl;
    protected final EntityPath<T> rootPath;

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
        this.predicateExecutor = new QuerydslJpaPredicateExecutor<>(JpaEntityInformationSupport.getEntityInformation(domainClass, em), em, SimpleEntityPathResolver.INSTANCE, getRepositoryMethodMetadata());
        this.rootPath = SimpleEntityPathResolver.INSTANCE.createPath(domainClass);
        this.querydsl = new Querydsl(em, new PathBuilder<>(rootPath.getType(), rootPath.getMetadata()));
    }

    @Override
    public Page<T> findAll(Predicate predicate, Pageable pageable) {
        JPAQuery<T> query = queryFactory.selectFrom(rootPath).where(predicate);
        JPQLQuery<T> countQuery = querydsl.applyPagination(pageable, query);
        return PageableExecutionUtils.getPage(query.fetch(), pageable, countQuery::fetchCount);
    }

    @Override
    public boolean isExist(Predicate... predicates) {
        return queryFactory
                .selectFrom(rootPath)
                .where(predicates)
                .limit(1)
                .fetchOne() != null;
    }
}
