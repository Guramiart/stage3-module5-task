package com.mjc.school.service.impl;


import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.service.AbstractService;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.mapper.AuthorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorService
        extends AbstractService<AuthorDtoRequest, AuthorDtoResponse, Long, Author, AuthorDtoRequest> {

    private final NewsRepository newsRepository;
    private final AuthorRepository authorRepository;
    private final AuthorMapper mapper;

    @Autowired
    public AuthorService(NewsRepository newsRepository, AuthorRepository authorRepository, AuthorMapper mapper) {
        super(authorRepository);
        this.authorRepository = authorRepository;
        this.newsRepository = newsRepository;
        this.mapper = mapper;
    }

    @Override
    protected String getErrorMessage() {
        return ServiceErrorCode.AUTHOR_ID_DOES_NOT_EXIST.getErrorMessage();
    }

    @Override
    protected List<AuthorDtoResponse> modelListToDto(List<Author> modelList) {
        return mapper.modelListToDto(modelList);
    }

    @Override
    protected AuthorDtoResponse modelToDto(Author model) {
        return mapper.modelToDto(model);
    }

    @Override
    protected Author dtoToModel(AuthorDtoRequest dto) {
        return mapper.dtoToModel(dto);
    }

    @Override
    protected Author updateDtoToModel(AuthorDtoRequest dto) {
        return mapper.dtoToModel(dto);
    }

    @Transactional(readOnly = true)
    public AuthorDtoResponse readByNewsId(Long newsId) {
        if(!newsRepository.existById(newsId)) {
            throw new NotFoundException(String.format(
                    ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getErrorMessage(), newsId
            ));
        }
        return authorRepository.readByNewsId(newsId)
                .map(mapper::modelToDto)
                .orElseThrow(() -> {
                    throw new NotFoundException(String.format(
                            ServiceErrorCode.AUTHOR_DOES_NOT_EXIST_FOR_NEWS_ID.getErrorMessage(), newsId)
                    );
                });
    }
}
