package com.mjc.school.service.mapper;

import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.Comment;
import com.mjc.school.service.dto.request.CommentDtoRequest;
import com.mjc.school.service.dto.response.CommentDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", uses = { NewsMapper.class })
public abstract class CommentMapper {

    @Autowired
    protected NewsRepository newsRepository;

    public abstract List<CommentDtoResponse> modelListToDto(List<Comment> commentList);

    @Mapping(target = "newsId", expression = "java(model.getNews().getId())")
    public abstract CommentDtoResponse modelToDto(Comment model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastUpdatedDate", ignore = true)
    @Mapping(target = "news", expression = "java(newsRepository.getReference(dto.newsId()))")
    public abstract Comment dtoToModel(CommentDtoRequest dto);
}
