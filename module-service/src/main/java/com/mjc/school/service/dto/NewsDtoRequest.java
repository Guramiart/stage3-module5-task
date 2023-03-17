package com.mjc.school.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public record NewsDtoRequest(
        @NotNull
        @Size(min = 5, max = 30, message = "News title length should be between from 5 to 30 chars")
        String title,

        @NotNull
        @Size(min = 5, max = 255, message = "")
        String content,

        @NotNull
        Long authorId,

        List<Long> tagIds,
        List<Long> commentsIds
) {
}
