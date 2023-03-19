package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.CommentRepository;
import com.mjc.school.repository.model.Comment;
import com.mjc.school.service.AbstractService;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService
        extends AbstractService<CommentDtoRequest, CommentDtoResponse, Long, Comment> {

    @Autowired
    protected CommentService(CommentRepository repository, CommentMapper mapper) {
        super(repository, mapper);
    }

    @Override
    protected String getErrorMessage() {
        return ServiceErrorCode.COMMENT_ID_DOES_NOT_EXIST.getErrorMessage();
    }
}
