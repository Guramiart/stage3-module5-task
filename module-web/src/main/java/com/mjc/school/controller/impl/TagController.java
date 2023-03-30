package com.mjc.school.controller.impl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.mjc.school.controller.AbstractController;
import com.mjc.school.constants.RestConstants;
import com.mjc.school.service.dto.query.NameSearchDtoRequest;
import com.mjc.school.service.dto.request.TagDtoRequest;
import com.mjc.school.service.dto.response.TagDtoResponse;
import com.mjc.school.service.impl.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(
        value = RestConstants.TAG_PATH,
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Api(value = "Operations for creating, updating, retrieving and deleting news tags in the application", tags = "Tags")
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
    @ApiOperation(value = "Retrieve all tags", response = TagDtoResponse.class)
    @ApiResponse(code = 200, message = "Successfully retrieve tags")
    public List<TagDtoResponse> readAll(NameSearchDtoRequest searchDtoRequest) {
        List<TagDtoResponse> tags = service.readAll(searchDtoRequest);
        tags.forEach(t -> t.add(linkTo(methodOn(getClass()).readById(t.getId())).withSelfRel()));
        return tags;
    }

    @Override
    @GetMapping(value = "/{id}", produces = RestConstants.VERSION_2)
    @ApiOperation(value = "Retrieve specific tag by supplied id", response = TagDtoResponse.class)
    @ApiResponse(code = 200, message = "Successfully retrieve specific tag")
    public TagDtoResponse readById(@PathVariable Long id) {
        return super.readById(id);
    }

    @Override
    @ApiResponse(code = 201, message = "Successfully create new tag")
    @ApiOperation(value = "Create new tag by name", response = TagDtoResponse.class)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = RestConstants.VERSION_1)
    public TagDtoResponse create(@RequestBody @Valid TagDtoRequest createRequest) {
        return super.create(createRequest);
    }

    @Override
    @ApiResponse(code = 200, message = "Successfully update specific tag")
    @ApiOperation(value = "Update specific tag by supplied id", response = TagDtoResponse.class)
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = RestConstants.VERSION_1)
    public TagDtoResponse update(@PathVariable Long id, @RequestBody @Valid TagDtoRequest updateRequest) {
        return super.update(id, updateRequest);
    }

    @Override
    @ApiResponse(code = 204, message = "Successfully delete specific tag")
    @ApiOperation(value = "Delete specific tag by supplied id", response = TagDtoResponse.class)
    @DeleteMapping(value = "/{id}", produces = RestConstants.VERSION_1)
    public void deleteById(@PathVariable Long id) {
        super.deleteById(id);
    }
}
