package com.mjc.school.controller;

import com.mjc.school.service.dto.SearchFilterDtoRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseController<T, R, K, U> {

    List<R> readAll(SearchFilterDtoRequest searchDtoRequest);

    R readById(K id);

    R create(T createRequest);

    R update(K id, U updateRequest);

    void deleteById(K id);

}
