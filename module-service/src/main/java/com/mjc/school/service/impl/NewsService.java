package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.AbstractService;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.mapper.NewsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService
        extends AbstractService<NewsDtoRequest, NewsDtoResponse, Long, NewsRepository, News, NewsMapper> {

    @Autowired
    protected NewsService(NewsRepository repository, NewsMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected String getErrorMessage() {
        return ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getErrorMessage();
    }
}
