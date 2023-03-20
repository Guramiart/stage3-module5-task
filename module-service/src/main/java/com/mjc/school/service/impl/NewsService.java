package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.impl.TagRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.AbstractService;
import com.mjc.school.service.dto.CreateNewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.UpdateNewsDtoRequest;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService
        extends AbstractService<CreateNewsDtoRequest, NewsDtoResponse, Long, News, UpdateNewsDtoRequest> {

    private final NewsMapper mapper;
    private final NewsRepository newsRepository;
    private final AuthorRepository authorRepository;

    private final TagRepository tagRepository;

    @Autowired
    public NewsService(
            NewsRepository newsRepository,
            AuthorRepository authorRepository,
            TagRepository tagRepository,
            NewsMapper mapper) {
        super(newsRepository);
        this.mapper = mapper;
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public NewsDtoResponse create(CreateNewsDtoRequest createRequest) {
        createAuthor(createRequest.author());
        createTags(createRequest.tags());
        News model = newsRepository.create(mapper.dtoToModel(createRequest));
        return mapper.modelToDto(model);
    }

    private void createAuthor(String name) {
        if(name != null && !name.isBlank()) {
            if(authorRepository.readByName(name).isEmpty()) {
                Author author = new Author();
                author.setName(name);
                authorRepository.create(author);
            }
        }
    }

    private void createTags(List<String> tags) {
        tags.stream()
                .filter(tag -> tagRepository.readByName(tag).isEmpty())
                .map(tagName -> {
                    Tag tag = new Tag();
                    tag.setName(tagName);
                    return tag;
                })
                .forEach(tagRepository::create);
    }

    @Override
    protected String getErrorMessage() {
        return ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getErrorMessage();
    }

    @Override
    protected List<NewsDtoResponse> modelListToDto(List<News> modelList) {
        return mapper.modelListToDto(modelList);
    }

    @Override
    protected NewsDtoResponse modelToDto(News model) {
        return mapper.modelToDto(model);
    }

    @Override
    protected News dtoToModel(CreateNewsDtoRequest dto) {
        return mapper.dtoToModel(dto);
    }

    @Override
    protected News updateDtoToModel(UpdateNewsDtoRequest dto) {
        return mapper.dtoToModel(dto);
    }
}
