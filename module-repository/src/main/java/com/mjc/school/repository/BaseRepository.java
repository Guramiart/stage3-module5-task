package com.mjc.school.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T extends BaseEntity<K>, K> {

    Page<T> readAll(Pageable pageable);

    Optional<T> readById(K id);

    T create(T entity);

    T update(T entity);

    boolean deleteById(K id);

    boolean existById(K id);

    T getReference(K id);

    List<T> readBySearchCriteria(Specification<Object> specification);

}
