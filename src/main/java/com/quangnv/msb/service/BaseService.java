package com.quangnv.msb.service;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface BaseService<I, T> {
    T findById(I id);

    List<T> findByIdIn(Iterable<I> ids);

    Page<T> findAll(Predicate predicate, Pageable pageable);

    Page<T> findAll(Pageable pageable);

    Page<T> findAll(Specification<T> spec, Pageable pageable);

    <A> List<T> findAll(String field, A value);

    List<T> findAll(Specification<T> spec, Sort sort);

    List<T> findAll(Specification<T> spec);

    List<T> findAll();

    void remove(T t);

    void removeById(I id);

    void removeByIdIn(Iterable<I> ids);

    void removeByIdInBatch(Iterable<I> ids);

    T create(T entity);

    List<T> create(Iterable<T> entities);

    T update(T entity);

    List<T> update(Iterable<T> entities);

    boolean exists(Specification<T> spec);

    boolean exists(Example<T> example);
}
