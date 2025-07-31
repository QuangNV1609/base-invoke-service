package com.quangnv.msb.repository;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<I extends Serializable, T> extends JpaRepositoryImplementation<T, I> {
    Page<T> findAll(Predicate predicate, Pageable pageable);

    boolean isExist(Predicate... predicates);
}
