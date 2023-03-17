package com.mjc.school.service.dto;

import java.util.List;

public record NewsDtoResponse(
        Long id,
        String title,
        String content,
        AuthorDtoResponse authorDto,
        List<TagDtoResponse> tagsDto,
        List<CommentDtoResponse> commentsDto
) {
}
