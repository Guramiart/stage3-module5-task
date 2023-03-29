package com.mjc.school.controller.impl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.mjc.school.controller.AbstractController;
import com.mjc.school.constants.PathConstant;
import com.mjc.school.service.dto.query.NewsSearchDtoRequest;
import com.mjc.school.service.dto.request.CreateNewsDtoRequest;
import com.mjc.school.service.dto.request.UpdateNewsDtoRequest;
import com.mjc.school.service.dto.response.AuthorDtoResponse;
import com.mjc.school.service.dto.response.CommentDtoResponse;
import com.mjc.school.service.dto.response.NewsDtoResponse;
import com.mjc.school.service.dto.response.TagDtoResponse;
import com.mjc.school.service.impl.AuthorService;
import com.mjc.school.service.impl.CommentService;
import com.mjc.school.service.impl.NewsService;
import com.mjc.school.service.impl.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(
        value = PathConstant.NEWS_PATH,
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@SuppressWarnings({"unchecked", "rawtypes"})
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
        List<NewsDtoResponse> news = newsService.readAll(searchDtoRequest);
        news.forEach(this::mapSelfLinks);
        news.stream()
                .map(NewsDtoResponse::getTagsDto)
                .forEach(tagDtoResponses -> tagDtoResponses
                        .forEach(t -> t.add(getEntityLinkById(TagController.class, t.getId()))));
        news.stream()
                .map(NewsDtoResponse::getAuthorDto)
                .forEach(a -> a.add(getEntityLinkById(AuthorController.class, a.getId())));
        news.stream()
                .map(NewsDtoResponse::getCommentsDto)
                .forEach(commentDtoResponses -> commentDtoResponses
                        .forEach(c -> c.add(getEntityLinkById(CommentController.class, c.getId()))));
        return news;
    }

    @Override
    public NewsDtoResponse create(@RequestBody @Valid CreateNewsDtoRequest createRequest) {
        NewsDtoResponse news =  super.create(createRequest);
        mapLinks(news);
        return news;
    }

    @Override
    public NewsDtoResponse readById(@PathVariable Long id) {
        NewsDtoResponse news = super.readById(id);
        mapLinks(news);
        return news;
    }

    @Override
    public NewsDtoResponse update(@PathVariable Long id, @RequestBody UpdateNewsDtoRequest updateRequest) {
        NewsDtoResponse news = super.update(id, updateRequest);
        mapLinks(news);
        return news;
    }

    @GetMapping(value = "/{id}/authors")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDtoResponse readAuthorByNewsId(@PathVariable Long id) {
        AuthorDtoResponse response = authorService.readByNewsId(id);
        response.add(getEntityLinkById(AbstractController.class, response.getId()));
        return response;
    }

    @GetMapping(value = "/{id}/tags")
    @ResponseStatus(HttpStatus.OK)
    public List<TagDtoResponse> readTagByNewsId(@PathVariable Long id) {
        List<TagDtoResponse> tags = tagService.readByNewsId(id);
        tags.forEach(t -> t.add(getEntityLinkById(TagController.class, t.getId())));
        return tags;
    }

    @GetMapping(value = "/{id}/comments")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDtoResponse> readCommentByNewsId(@PathVariable Long id) {
        List<CommentDtoResponse> comments = commentService.readByNewsId(id);
        comments.forEach(c -> c.add(getEntityLinkById(CommentController.class, c.getId())));
        return comments;
    }

    private void mapSelfLinks(NewsDtoResponse response) {
        response.add(linkTo(methodOn(NewsController.class).readById(response.getId())).withSelfRel());
        response.add(linkTo(methodOn(AuthorController.class).readById(response.getId())).withRel("author"));
        response.add(linkTo(methodOn(NewsController.class).readTagByNewsId(response.getId())).withRel("tags"));
        response.add(linkTo(methodOn(NewsController.class).readCommentByNewsId(response.getId())).withRel("comments"));
    }

    private void mapLinks(NewsDtoResponse response) {
        mapSelfLinks(response);
        response.getAuthorDto().add(getEntityLinkById(AuthorController.class ,response.getAuthorDto().getId()));
        response.getTagsDto().forEach(t -> t.add(getEntityLinkById(TagController.class, t.getId())));
        response.getCommentsDto().forEach(c -> c.add(getEntityLinkById(CommentController.class, c.getId())));
    }

    private Link getEntityLinkById(Class<? extends AbstractController> clazz, Long id) {
        return linkTo(methodOn(clazz).readById(id)).withSelfRel();
    }

}
