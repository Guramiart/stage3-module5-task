package com.mjc.school.controller.impl;

import com.mjc.school.controller.AbstractController;
import com.mjc.school.constants.PathConstant;
import com.mjc.school.service.dto.*;
import com.mjc.school.service.dto.query.NewsSearchDtoRequest;
import com.mjc.school.service.impl.AuthorService;
import com.mjc.school.service.impl.CommentService;
import com.mjc.school.service.impl.NewsService;
import com.mjc.school.service.impl.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = PathConstant.NEWS_PATH,
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class NewsController
        extends AbstractController<CreateNewsDtoRequest, NewsDtoResponse, Long, NewsService, UpdateNewsDtoRequest, NewsSearchDtoRequest> {

    private final NewsService newsService;
    private final AuthorService authorService;
    private final TagService tagService;
    private final CommentService commentService;
    @Autowired
    protected NewsController(NewsService newsService, AuthorService authorService, TagService tagService, CommentService commentService) {
        super(newsService);
        this.newsService = newsService;
        this.authorService = authorService;
        this.tagService = tagService;
        this.commentService = commentService;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NewsDtoResponse> readAll(NewsSearchDtoRequest searchDtoRequest) {
        return newsService.readAll(searchDtoRequest);
    }

    @GetMapping(value = "/{id}/authors")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDtoResponse readAuthorByNewsId(@PathVariable Long id) {
        return authorService.readByNewsId(id);
    }

    @GetMapping(value = "/{id}/tags")
    @ResponseStatus(HttpStatus.OK)
    public List<TagDtoResponse> readTagByNewsId(@PathVariable Long id) {
        return tagService.readByNewsId(id);
    }

    @GetMapping(value = "/{id}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDtoResponse> readCommentByNewsId(@PathVariable Long id) {
        return commentService.readByNewsId(id);
    }
}
