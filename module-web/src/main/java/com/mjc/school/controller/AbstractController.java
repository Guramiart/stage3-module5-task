package com.mjc.school.controller;

import com.mjc.school.service.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

public abstract class AbstractController<T, R, K, S extends BaseService<T, R, K, U, P>, U,  P>
        implements BaseController<T, R, K, U, P> {

    private final BaseService<T, R, K, U, P> service;

    protected AbstractController(S service) {
        this.service = service;
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
    public R create(@RequestBody @Valid T createRequest) {
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

}
