package com.mjc.school.service.impl;

import com.mjc.school.repository.impl.CommentRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.Comment;
import com.mjc.school.repository.query.SearchQueryParam;
import com.mjc.school.service.AbstractService;
import com.mjc.school.service.dto.request.CommentDtoRequest;
import com.mjc.school.service.dto.response.CommentDtoResponse;
import com.mjc.school.service.dto.query.SortDtoRequest;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService
        extends AbstractService<CommentDtoRequest, CommentDtoResponse, Long, Comment, CommentDtoRequest, SortDtoRequest> {

    private final CommentMapper mapper;
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    @Autowired
    public CommentService(NewsRepository newsRepository, CommentRepository commentRepository, CommentMapper mapper) {
        super(commentRepository);
        this.newsRepository = newsRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDtoResponse> readAll(SortDtoRequest searchDtoRequest) {
        Pageable pageable = PageRequest.of(searchDtoRequest.getPage(), searchDtoRequest.getSize());
        SearchQueryParam searchQueryParam = new SearchQueryParam.Builder(pageable)
                .sortBy(searchDtoRequest.getSortBy())
                .order(searchDtoRequest.getOrder())
                .build();
        return modelListToDto(commentRepository.readAll(searchQueryParam).getContent());
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
