package com.mjc.school.controller;

import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.SearchFilterDtoRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractController<T, R, K, S extends BaseService<T, R, K, U>, U> implements BaseController<T, R, K, U> {

    private final BaseService<T, R, K, U> service;

    protected AbstractController(S service) {
        this.service = service;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<R> readAll(Pageable pageable) {
        return service.readAll(pageable);
    }

    @Override
    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public R readById(@PathVariable K id) {
        return service.readById(id);
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public R create(@RequestBody T createRequest) {
        return service.create(createRequest);
    }

    @Override
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public R update(@PathVariable K id, @RequestBody U updateRequest) {
        return service.update(id, updateRequest);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable K id) {
        service.deleteById(id);
    }

    @GetMapping(value = "/search")
    public void readBySearchCriteria(SearchFilterDtoRequest searchDtoRequest) {
        service.readBySearchCriteria(searchDtoRequest);
    }
}
