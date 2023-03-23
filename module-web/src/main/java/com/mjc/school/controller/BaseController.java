package com.mjc.school.controller;

import java.util.List;

public interface BaseController<T, R, K, U, P> {

    List<R> readAll(P searchDtoRequest);

    R readById(K id);

    R create(T createRequest);

    R update(K id, U updateRequest);

    void deleteById(K id);

}
