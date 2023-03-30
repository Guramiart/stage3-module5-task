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
    @ApiResponse(code = 200, message = "Successfully retrieve comments")
    public List<CommentDtoResponse> readAll(SortDtoRequest searchDtoRequest) {
        return service.readAll(searchDtoRequest);
    }

    @Override
    @GetMapping(value = "/{id}", produces = RestConstants.VERSION_2)
    @ApiOperation(value = "Retrieve specific comment by supplied id", response = CommentDtoResponse.class)
    @ApiResponse(code = 200, message = "Successfully retrieve specific comment")
    public CommentDtoResponse readById(@PathVariable Long id) {
        return super.readById(id);
    }

    @Override
    @ApiResponse(code = 201, message = "Successfully create new comment")
    @ApiOperation(value = "Create new comment", response = CommentDtoResponse.class)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = RestConstants.VERSION_1)
    public CommentDtoResponse create(@RequestBody @Valid CommentDtoRequest createRequest) {
        return super.create(createRequest);
    }

    @Override
    @ApiResponse(code = 200, message = "Successfully update specific comment")
    @ApiOperation(value = "Update specific comment by supplied id", response = CommentDtoResponse.class)
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = RestConstants.VERSION_1)
    public CommentDtoResponse update(@PathVariable Long id, @RequestBody @Valid CommentDtoRequest updateRequest) {
        return super.update(id, updateRequest);
    }

    @Override
    @ApiResponse(code = 204, message = "Successfully delete specific comment")
    @ApiOperation(value = "Delete specific comment by supplied id", response = CommentDtoResponse.class)
    @DeleteMapping(value = "/{id}", produces = RestConstants.VERSION_1)
    public void deleteById(@PathVariable Long id) {
        super.deleteById(id);
    }
}
