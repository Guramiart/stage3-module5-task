package com.mjc.school.controller.impl;

import com.mjc.school.controller.AbstractController;
import com.mjc.school.constants.PathConstant;
import com.mjc.school.service.dto.NameSearchDtoRequest;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.impl.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = PathConstant.TAG_PATH,
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class TagController
        extends AbstractController<TagDtoRequest, TagDtoResponse, Long, TagService, TagDtoRequest, NameSearchDtoRequest> {

    private final TagService service;
    @Autowired
    protected TagController(TagService service) {
        super(service);
        this.service = service;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TagDtoResponse> readAll(NameSearchDtoRequest searchDtoRequest) {
        return service.readAll(searchDtoRequest);
    }
}
