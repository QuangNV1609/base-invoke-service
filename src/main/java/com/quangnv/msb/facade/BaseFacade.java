package com.quangnv.msb.facade;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@Facade
public interface BaseFacade<I, E> {
    <R, D> R create(D payload, Function<D, E> mergeFunc, Function<E, R> mapper);

    <R> R create(E entity, Function<E, R> mapper);

    E create(E entity);

    <R, D> R update(D payload, I id, BiFunction<D, E, E> mergeFunc, Function<E, R> mapper);

    <R> R update(E entity, Function<E, R> mapper);

    E update(E entity);

    <R> R findById(I id, Function<E, R> mapper);

    E findById(I id);

    <R> List<R> findByIdIn(Iterable<I> ids, Function<E, R> mapper);

    List<E> findByIdIn(Iterable<I> ids);

    List<E> findAll();

    <R> List<R> findAll(Function<E, R> mapper);

    <R> Page<R> findAll(Specification<E> spec, Pageable pageable, Function<E, R> mapper);

    Page<E> findAll(Pageable pageable);

    <R> Page<R> findAll(Pageable pageable, Function<E, R> mapper);

    <R> List<R> findAll(Specification<E> spec, Function<E, R> mapper);

    <R> List<R> findAll(Specification<E> spec, Sort sort, Function<E, R> mapper);

    <R> Page<R> findAll(Predicate predicate, Pageable pageable, Function<E, R> mapper);

    <R, A> List<R> findAll(String field, A value, Function<E, R> mapper);

    void remove(E entity);

    void removeById(I id);

    void removeByIdIn(Iterable<I> ids);

    void removeByIdInBatch(Iterable<I> ids);

}
