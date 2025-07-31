package com.quangnv.msb.facade.impl;

import com.quangnv.msb.core.funtional.Maybe;
import com.querydsl.core.types.Predicate;
import com.quangnv.msb.facade.BaseFacade;
import com.quangnv.msb.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * This class provides common CRUD functions
 *
 * @param <I> Id class
 * @param <E> Entity class
 * @param <S> Primary Service class
 * @author toaidx1
 */
@Transactional(readOnly = true)
public abstract class BaseFacadeImpl<I, E, S extends BaseService<I, E>> implements BaseFacade<I, E> {
    protected final S service;

    protected BaseFacadeImpl(S service) {
        this.service = service;
    }

    @Transactional
    @Override
    public <R, D> R create(D payload, Function<D, E> mergeFunc, Function<E, R> mapper) {
        E mergeEntity = mergeFunc.apply(payload);
        E createdEntity = service.create(mergeEntity);
        return mapper.apply(createdEntity);
    }

    @Transactional
    @Override
    public <R> R create(E entity, Function<E, R> mapper) {
        E createdEntity = service.create(entity);
        return mapper.apply(createdEntity);
    }

    @Transactional
    @Override
    public E create(E entity) {
        return service.create(entity);
    }

    @Transactional
    @Override
    public <R, D> R update(D payload, I id, BiFunction<D, E, E> mergeFunc, Function<E, R> mapper) {
        E entity = findById(id);
        E mergedEntity = mergeFunc.apply(payload, entity);
        E updatedEntity = service.update(mergedEntity);
        return mapper.apply(updatedEntity);
    }

    @Transactional
    @Override
    public <R> R update(E entity, Function<E, R> mapper) {
        E updatedEntity = service.update(entity);
        return mapper.apply(updatedEntity);
    }

    @Transactional
    @Override
    public E update(E entity) {
        return service.update(entity);
    }

    @Override
    public <D> D findById(I id, Function<E, D> mapper) {
        return Maybe.of(id).map(service::findById)
                .map(mapper::apply)
                .orElseThrow(
                        new IllegalStateException("No records with given id found!")
                );
    }

    @Override
    public E findById(I id) {
        return findById(id, e -> e);
    }

    @Override
    public List<E> findByIdIn(Iterable<I> ids) {
        return service.findByIdIn(ids);
    }

    @Override
    public <R> List<R> findByIdIn(Iterable<I> ids, Function<E, R> mapper) {
        return findByIdIn(ids).stream().map(mapper::apply).collect(Collectors.toList());
    }

    @Override
    public <R> Page<R> findAll(Predicate predicate, Pageable pageable, Function<E, R> mapper) {
        return service.findAll(predicate, pageable).map(mapper::apply);
    }

    @Override
    public <R> Page<R> findAll(Specification<E> spec, Pageable pageable, Function<E, R> mapper) {
        return service.findAll(spec, pageable).map(mapper::apply);
    }

    @Override
    public <R> List<R> findAll(Specification<E> spec, Sort sort, Function<E, R> mapper) {
        return service.findAll(spec, sort).stream().map(mapper::apply).collect(Collectors.toList());
    }

    @Override
    public <D> Page<D> findAll(Pageable pageable, Function<E, D> mapper) {
        return service.findAll(pageable).map(mapper::apply);
    }

    @Override
    public Page<E> findAll(Pageable pageable) {
        return findAll(pageable, e -> e);
    }

    @Override
    public <R, A> List<R> findAll(String field, A value, Function<E, R> mapper) {
        return service.findAll(field, value).stream().map(mapper::apply).collect(Collectors.toList());
    }

    @Override
    public List<E> findAll() {
        return service.findAll();
    }

    @Override
    public <R> List<R> findAll(Function<E, R> mapper) {
        return service.findAll().stream().map(mapper::apply).collect(Collectors.toList());
    }

    @Override
    public <R> List<R> findAll(Specification<E> spec, Function<E, R> mapper) {
        return service.findAll(spec).stream().map(mapper::apply).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void remove(E entity) {
        service.remove(entity);
    }

    @Transactional
    @Override
    public void removeById(I id) {
        service.removeById(id);
    }

    @Transactional
    @Override
    public void removeByIdIn(Iterable<I> ids) {
        service.removeByIdIn(ids);
    }

    @Transactional
    @Override
    public void removeByIdInBatch(Iterable<I> ids) {
        service.removeByIdInBatch(ids);
    }
}
