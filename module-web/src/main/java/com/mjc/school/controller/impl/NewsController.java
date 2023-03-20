package com.mjc.school.controller.impl;

import com.mjc.school.controller.AbstractController;
import com.mjc.school.constants.PathConstant;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.dto.CreateNewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.UpdateNewsDtoRequest;
import com.mjc.school.service.impl.CommentService;
import com.mjc.school.service.impl.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = PathConstant.NEWS_PATH,
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class NewsController extends AbstractController<CreateNewsDtoRequest, NewsDtoResponse, Long, NewsService, UpdateNewsDtoRequest> {

    private final CommentService commentService;
    @Autowired
    protected NewsController(NewsService service, CommentService commentService) {
        super(service);
        this.commentService = commentService;
    }

    @GetMapping(value = "/{id}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDtoResponse> readByNewsId(@PathVariable Long id) {
        return commentService.readByNewsId(id);
    }
}
