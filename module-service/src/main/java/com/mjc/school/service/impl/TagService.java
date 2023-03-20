package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.TagRepository;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.AbstractService;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService extends AbstractService<TagDtoRequest, TagDtoResponse, Long, Tag, TagDtoRequest> {

    private final TagMapper mapper;
    @Autowired
    public TagService(TagRepository repository, TagMapper mapper) {
        super(repository);
        this.mapper = mapper;
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
