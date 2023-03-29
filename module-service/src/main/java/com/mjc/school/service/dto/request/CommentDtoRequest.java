package com.mjc.school.service.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record CommentDtoRequest(
        @NotNull
        @Size(min = 3, max = 255, message = "Comment content length should be between from 3 to 255 chars")
        String content,

        @NotNull
        Long newsId
) {
}
