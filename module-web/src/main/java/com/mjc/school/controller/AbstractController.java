package com.mjc.school.controller;

import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.response.ResponseEntity;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ResponseStatus(HttpStatus.OK)
    public abstract List<R> readAll(P searchRequest);

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public R readById(K id) {
        R response = service.readById(id);
        response.add(getLink(id));
        return response;
    }

    @Override
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public R create(T createRequest) {
        R response = service.create(createRequest);
        response.add(getLink(response.getId()));
        return response;
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public R update(K id, U updateRequest) {
        R response = service.update(id, updateRequest);
        response.add(getLink(id));
        return response;
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public void deleteById(K id) {
        service.deleteById(id);
    }

    private Link getLink(K id) {
        return linkTo(methodOn(getClass()).readById(id)).withSelfRel();
    }

}
