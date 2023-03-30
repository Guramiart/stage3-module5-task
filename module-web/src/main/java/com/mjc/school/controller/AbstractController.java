package com.mjc.school.controller;

import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.response.ResponseEntity;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@SuppressWarnings("unchecked")
public abstract class AbstractController<T, R extends ResponseEntity<K>, K, S extends BaseService<T, R, K, U, P>, U,  P>
        implements BaseController<T, R, K, U, P> {
    private final BaseService<T, R, K, U, P> service;

    protected AbstractController(S service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.OK)
    public abstract List<R> readAll(P searchRequest);

    @Override
    @ResponseStatus(HttpStatus.OK)
    public R readById(K id) {
        R response = service.readById(id);
        response.add(getLink(id));
        return response;
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    public R create(T createRequest) {
        R response = service.create(createRequest);
        response.add(getLink(response.getId()));
        return response;
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    public R update(K id, U updateRequest) {
        R response = service.update(id, updateRequest);
        response.add(getLink(id));
        return response;
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(K id) {
        service.deleteById(id);
    }

    private Link getLink(K id) {
        return linkTo(methodOn(getClass()).readById(id)).withSelfRel();
    }

}
