package com.mjc.school.repository;

import com.mjc.school.repository.filter.EntityRequest;
import com.mjc.school.repository.filter.EntitySpecification;
import com.mjc.school.repository.query.SearchQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T extends BaseEntity<K>, K> {

    Page<T> readAll(SearchQueryParam request);

    Optional<T> readById(K id);

    T create(T entity);

    T update(T entity);

    boolean deleteById(K id);

    boolean existById(K id);

    T getReference(K id);

}
