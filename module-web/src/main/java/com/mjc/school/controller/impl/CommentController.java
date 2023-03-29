package com.mjc.school.controller.impl;

import com.mjc.school.controller.AbstractController;
import com.mjc.school.constants.PathConstant;
import com.mjc.school.service.dto.request.CommentDtoRequest;
import com.mjc.school.service.dto.response.CommentDtoResponse;
import com.mjc.school.service.dto.query.SortDtoRequest;
import com.mjc.school.service.impl.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = PathConstant.COMMENT_PATH,
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
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
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDtoResponse> readAll(SortDtoRequest searchDtoRequest) {
        return service.readAll(searchDtoRequest);
    }
}
