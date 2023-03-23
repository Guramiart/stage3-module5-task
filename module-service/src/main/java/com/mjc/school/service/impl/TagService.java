package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.impl.TagRepository;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.repository.query.SearchQueryParam;
import com.mjc.school.service.AbstractService;
import com.mjc.school.service.dto.NameSearchDtoRequest;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagService extends AbstractService<TagDtoRequest, TagDtoResponse, Long, Tag, TagDtoRequest, NameSearchDtoRequest> {

    private final NewsRepository newsRepository;
    private final TagRepository tagRepository;
    private final TagMapper mapper;
    @Autowired
    public TagService(NewsRepository newsRepository, TagRepository tagRepository, TagMapper mapper) {
        super(tagRepository);
        this.newsRepository = newsRepository;
        this.tagRepository = tagRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagDtoResponse> readAll(NameSearchDtoRequest searchDtoRequest) {
        Pageable pageable = PageRequest.of(searchDtoRequest.getPage(), searchDtoRequest.getSize());
        SearchQueryParam searchQueryParam = new SearchQueryParam.Builder(pageable)
                .name(searchDtoRequest.getName())
                .build();
        return modelListToDto(tagRepository.readAll(searchQueryParam).getContent());
    }

    @Transactional(readOnly = true)
    public List<TagDtoResponse> readByNewsId(Long newsId) {
        if(!newsRepository.existById(newsId)) {
            throw new NotFoundException(String.format(
                    ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getErrorMessage(), newsId)
            );
        }
        List<TagDtoResponse> tags = mapper.modelListToDto(tagRepository.readByNewsId(newsId));
        if(tags.isEmpty()) {
            throw new NotFoundException(String.format(
                    ServiceErrorCode.TAG_DOES_NOT_EXIST_FOR_NEWS_ID.getErrorMessage(), newsId
            ));
        }
        return tags;
    }

    @Override
    protected String getErrorMessage() {
        return ServiceErrorCode.TAG_ID_DOES_NOT_EXIST.getErrorMessage();
    }

    @Override
    protected List<TagDtoResponse> modelListToDto(List<Tag> modelList) {
        return mapper.modelListToDto(modelList);
    }

    @Override
    protected TagDtoResponse modelToDto(Tag model) {
        return mapper.modelToDto(model);
    }

    @Override
    protected Tag dtoToModel(TagDtoRequest dto) {
        return mapper.dtoToModel(dto);
    }

    @Override
    protected Tag updateDtoToModel(TagDtoRequest dto) {
        return mapper.dtoToModel(dto);
    }

}
