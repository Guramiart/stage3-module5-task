package com.mjc.school.controller.impl;

import com.mjc.school.controller.AbstractController;
import com.mjc.school.constants.RestConstants;
import com.mjc.school.service.dto.request.AuthorDtoRequest;
import com.mjc.school.service.dto.response.AuthorDtoResponse;
import com.mjc.school.service.dto.query.NameSearchDtoRequest;
import com.mjc.school.service.dto.response.TagDtoResponse;
import com.mjc.school.service.impl.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(
        value = RestConstants.AUTHOR_PATH,
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Api(value = "Operations for creating, updating, retrieving and deleting authors in the application", tags = "Author")
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
    @ApiOperation(value = "Retrieve all authors", response = AuthorDtoResponse.class)
    @ApiResponse(code = 200, message = "Successfully retrieve authors")
    public List<AuthorDtoResponse> readAll(NameSearchDtoRequest searchDtoRequest) {
        return service.readAll(searchDtoRequest);
    }

    @Override
    @GetMapping(value = "/{id}", produces = RestConstants.VERSION_2)
    @ApiOperation(value = "Retrieve specific author by supplied id", response = AuthorDtoResponse.class)
    @ApiResponse(code = 200, message = "Successfully retrieve specific author")
    public AuthorDtoResponse readById(@PathVariable Long id) {
        return super.readById(id);
    }

    @Override
    @ApiResponse(code = 201, message = "Successfully create new author")
    @ApiOperation(value = "Create new author by name", response = AuthorDtoResponse.class)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = RestConstants.VERSION_1)
    public AuthorDtoResponse create(@RequestBody AuthorDtoRequest createRequest) {
        return super.create(createRequest);
    }

    @Override
    @ApiResponse(code = 200, message = "Successfully update specific author")
    @ApiOperation(value = "Update specific author by supplied id", response = AuthorDtoResponse.class)
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = RestConstants.VERSION_1)
    public AuthorDtoResponse update(@PathVariable Long id, @RequestBody @Valid AuthorDtoRequest updateRequest) {
        return super.update(id, updateRequest);
    }

    @Override
    @ApiResponse(code = 204, message = "Successfully delete specific author")
    @ApiOperation(value = "Delete specific author by supplied id", response = TagDtoResponse.class)
    @DeleteMapping(value = "/{id}", produces = RestConstants.VERSION_1)
    public void deleteById(@PathVariable Long id) {
        super.deleteById(id);
    }
}
