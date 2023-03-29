package com.mjc.school.controller.impl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.mjc.school.controller.AbstractController;
import com.mjc.school.constants.PathConstant;
import com.mjc.school.service.dto.query.NameSearchDtoRequest;
import com.mjc.school.service.dto.request.TagDtoRequest;
import com.mjc.school.service.dto.response.TagDtoResponse;
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
        List<TagDtoResponse> tags = service.readAll(searchDtoRequest);
        tags.forEach(t -> t.add(linkTo(methodOn(getClass()).readById(t.getId())).withSelfRel()));
        return tags;
    }
}
