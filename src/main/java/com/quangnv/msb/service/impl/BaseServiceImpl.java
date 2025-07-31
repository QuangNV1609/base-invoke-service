package com.quangnv.msb.service.impl;

import com.querydsl.core.types.Predicate;
import com.quangnv.msb.repository.BaseRepository;
import com.quangnv.msb.service.BaseService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.StreamSupport;


public abstract class BaseServiceImpl<I extends Serializable, T, R extends BaseRepository<I, T>> implements BaseService<I, T> {
    protected final R repository;

    protected BaseServiceImpl(R repository) {
        this.repository = repository;
    }

    @Override
    public T findById(I id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<T> findByIdIn(Iterable<I> ids) {
        if (ids == null || StreamSupport.stream(ids.spliterator(), false).count() == 0) {
            return Collections.emptyList();
        }
        return repository.findAllById(ids);
    }

    public Page<T> findAll(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate, pageable);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }

    @Override
    public List<T> findAll(Specification<T> spec, Sort sort) {
        return repository.findAll(spec, sort);
    }

    @Override
    public List<T> findAll(Specification<T> spec) {
        return repository.findAll(spec);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public <A> List<T> findAll(String field, A value) {
        Specification<T> spec = (root, query, cb) -> cb.equal(root.get(field), value);
        return findAll(spec);
    }

    @Override
    public T create(T entity) {
        return repository.save(entity);
    }

    @Override
    public List<T> create(Iterable<T> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public T update(T entity) {
        return repository.save(entity);
    }

    @Override
    public List<T> update(Iterable<T> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public void remove(T t) {
        repository.delete(t);
    }

    @Override
    public void removeById(I id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void removeByIdIn(Iterable<I> ids) {
        repository.deleteAllById(ids);
    }

    @Override
    @Transactional
    public void removeByIdInBatch(Iterable<I> ids) {
        repository.deleteAllByIdInBatch(ids);
    }

    @Override
    public boolean exists(Example<T> example) {
        return repository.exists(example);
    }

    @Override
    public boolean exists(Specification<T> spec) {
        return repository.exists(spec);
    }
}

