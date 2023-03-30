package com.mjc.school.controller.impl;

import com.mjc.school.controller.AbstractController;
import com.mjc.school.constants.RestConstants;
import com.mjc.school.service.dto.request.CommentDtoRequest;
import com.mjc.school.service.dto.response.CommentDtoResponse;
import com.mjc.school.service.dto.query.SortDtoRequest;
import com.mjc.school.service.impl.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(
        value = RestConstants.COMMENT_PATH,
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Api(value = "Operations for creating, updating, retrieving and deleting news comments in the application", tags = "Comments")
public class CommentController
        extends AbstractController<CommentDtoRequest, CommentDtoResponse, Long, CommentService, CommentDtoRequest, SortDtoRequest> {

    private final CommentService service;
    @Autowired
    protected CommentController(CommentService service) {
        super(service);
        this.service = service;
    }

    @Override
    @GetMapping
    @ApiOperation(value = "Retrieve all news", response = CommentDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve comments"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public List<CommentDtoResponse> readAll(SortDtoRequest searchDtoRequest) {
        return service.readAll(searchDtoRequest);
    }

    @Override
    @GetMapping(value = "/{id}", produces = RestConstants.VERSION_2)
    @ApiOperation(value = "Retrieve specific comment by supplied id", response = CommentDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve specific comment by id"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public CommentDtoResponse readById(@PathVariable Long id) {
        return super.readById(id);
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully create new comment"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = "Create new comment", response = CommentDtoResponse.class)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = RestConstants.VERSION_1)
    public CommentDtoResponse create(@RequestBody @Valid CommentDtoRequest createRequest) {
        return super.create(createRequest);
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update specific comment"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = "Update specific comment by supplied id", response = CommentDtoResponse.class)
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = RestConstants.VERSION_1)
    public CommentDtoResponse update(@PathVariable Long id, @RequestBody @Valid CommentDtoRequest updateRequest) {
        return super.update(id, updateRequest);
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully delete specific comment"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiOperation(value = "Delete specific comment by supplied id", response = CommentDtoResponse.class)
    @DeleteMapping(value = "/{id}", produces = RestConstants.VERSION_1)
    public void deleteById(@PathVariable Long id) {
        super.deleteById(id);
    }
}
