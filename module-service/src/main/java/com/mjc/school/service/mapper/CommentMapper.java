package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.Comment;
import com.mjc.school.service.dto.CommentDtoRequest;
import com.mjc.school.service.dto.CommentDtoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class CommentMapper implements BaseMapper<CommentDtoRequest, CommentDtoResponse, Comment> {
}
