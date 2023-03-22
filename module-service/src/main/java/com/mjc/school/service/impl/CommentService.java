package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.CommentRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.Comment;
import com.mjc.school.service.AbstractService;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.mapper.BaseSearchMapper;
import com.mjc.school.service.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService
        extends AbstractService<CommentDtoRequest, CommentDtoResponse, Long, Comment, CommentDtoRequest> {

    private final CommentMapper mapper;
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    @Autowired
    public CommentService(NewsRepository newsRepository, CommentRepository commentRepository, CommentMapper mapper, BaseSearchMapper searchMapper) {
        super(commentRepository, searchMapper);
        this.newsRepository = newsRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public CommentDtoResponse create(CommentDtoRequest createRequest) {
        if(newsRepository.existById(createRequest.newsId())) {
            return super.create(createRequest);
        } else {
            throw new NotFoundException(
                    String.format(ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getErrorMessage(), createRequest.newsId())
            );
        }
    }

    @Transactional(readOnly = true)
    public List<CommentDtoResponse> readByNewsId(Long newsId) {
        if(!newsRepository.existById(newsId)) {
            throw new NotFoundException(
                    String.format(ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getErrorMessage(), newsId)
            );
        }
        List<CommentDtoResponse> responseList = mapper.modelListToDto(commentRepository.readByNewsId(newsId));
        if(responseList.isEmpty()) {
            throw new NotFoundException(String.format(
                    ServiceErrorCode.COMMENT_DOES_NOT_EXIST_FOR_NEWS_ID.getErrorMessage(), newsId
            ));
        }
        return responseList;
    }
    @Override
    protected String getErrorMessage() {
        return ServiceErrorCode.COMMENT_ID_DOES_NOT_EXIST.getErrorMessage();
    }

    @Override
    protected List<CommentDtoResponse> modelListToDto(List<Comment> modelList) {
        return mapper.modelListToDto(modelList);
    }

    @Override
    protected CommentDtoResponse modelToDto(Comment model) {
        return mapper.modelToDto(model);
    }

    @Override
    protected Comment dtoToModel(CommentDtoRequest dto) {
        return mapper.dtoToModel(dto);
    }

    @Override
    protected Comment updateDtoToModel(CommentDtoRequest dto) {
        return mapper.dtoToModel(dto);
    }
}
