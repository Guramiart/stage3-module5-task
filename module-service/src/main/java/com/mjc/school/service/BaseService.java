package com.mjc.school.service;

import java.util.List;

public interface BaseService<T, R, K, U, P> {

    List<R> readAll(P searchDtoRequest);

    R readById(K id);

    R create(T createRequest);

    R update(K id, U updateRequest);

    void deleteById(K id);

}
