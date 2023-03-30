package com.mjc.school.controller.impl;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.mjc.school.controller.AbstractController;
import com.mjc.school.constants.RestConstants;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(
        value = RestConstants.NEWS_PATH,
        produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Api(value = "Operations for creating, updating, retrieving and deleting news in the application", tags = "News")
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
    @ApiOperation(value = "Retrieve all news", response = CommentDtoResponse.class)
    @ApiResponse(code = 200, message = "Successfully retrieve news")
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
    @ApiResponse(code = 201, message = "Successfully create new news")
    @ApiOperation(value = "Create new news", response = NewsDtoResponse.class)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = RestConstants.VERSION_1)
    public NewsDtoResponse create(@RequestBody @Valid CreateNewsDtoRequest createRequest) {
        NewsDtoResponse news =  super.create(createRequest);
        mapLinks(news);
        return news;
    }

    @Override
    @GetMapping(value = "/{id}", produces = RestConstants.VERSION_2)
    @ApiOperation(value = "Retrieve specific news by supplied id", response = NewsDtoResponse.class)
    @ApiResponse(code = 200, message = "Successfully retrieve specific news")
    public NewsDtoResponse readById(@PathVariable Long id) {
        NewsDtoResponse news = super.readById(id);
        mapLinks(news);
        return news;
    }

    @Override
    @ApiResponse(code = 200, message = "Successfully update specific news")
    @ApiOperation(value = "Update specific news by supplied id", response = NewsDtoResponse.class)
    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = RestConstants.VERSION_1)
    public NewsDtoResponse update(@PathVariable Long id, @RequestBody @Valid UpdateNewsDtoRequest updateRequest) {
        NewsDtoResponse news = super.update(id, updateRequest);
        mapLinks(news);
        return news;
    }

    @Override
    @ApiResponse(code = 204, message = "Successfully delete specific news")
    @ApiOperation(value = "Delete specific news by supplied id", response = NewsDtoResponse.class)
    @DeleteMapping(value = "/{id}", produces = RestConstants.VERSION_1)
    public void deleteById(@PathVariable Long id) {
        super.deleteById(id);
    }

    @GetMapping(value = "/{id}/authors")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(code = 200, message = "Successfully retrieve specific author by news id")
    @ApiOperation(value = "Retrieve specific news by supplied id", response = AuthorDtoResponse.class)
    public AuthorDtoResponse readAuthorByNewsId(@PathVariable Long id) {
        AuthorDtoResponse response = authorService.readByNewsId(id);
        response.add(getEntityLinkById(AbstractController.class, response.getId()));
        return response;
    }

    @GetMapping(value = "/{id}/tags")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(code = 200, message = "Successfully retrieve specific tags by news id")
    @ApiOperation(value = "Retrieve specific tags by supplied news id", response = TagDtoResponse.class)
    public List<TagDtoResponse> readTagByNewsId(@PathVariable Long id) {
        List<TagDtoResponse> tags = tagService.readByNewsId(id);
        tags.forEach(t -> t.add(getEntityLinkById(TagController.class, t.getId())));
        return tags;
    }

    @GetMapping(value = "/{id}/comments")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(code = 200, message = "Successfully retrieve specific comments by news id")
    @ApiOperation(value = "Retrieve specific comments by news id", response = CommentDtoResponse.class)
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
