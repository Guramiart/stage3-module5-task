package com.mjc.school.controller.impl;

import com.mjc.school.controller.AbstractController;
import com.mjc.school.constants.PathConstant;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NameSearchDtoRequest;
import com.mjc.school.service.impl.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
        value = PathConstant.AUTHOR_PATH,
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class AuthorController
        extends AbstractController<AuthorDtoRequest, AuthorDtoResponse, Long, AuthorService, AuthorDtoRequest, NameSearchDtoRequest> {

    private final AuthorService service;
    @Autowired
    protected AuthorController(AuthorService service) {
        super(service);
        this.service = service;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorDtoResponse> readAll(NameSearchDtoRequest searchDtoRequest) {
        return service.readAll(searchDtoRequest);
    }
}
