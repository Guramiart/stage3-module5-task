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

@Service
public class TagService extends AbstractService<TagDtoRequest, TagDtoResponse, Long, TagRepository, Tag, TagMapper> {

    @Autowired
    protected TagService(TagRepository repository, TagMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected String getErrorMessage() {
        return ServiceErrorCode.TAG_ID_DOES_NOT_EXIST.getErrorMessage();
    }

}
