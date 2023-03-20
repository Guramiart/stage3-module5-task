package com.mjc.school.service.dto;

import org.springframework.lang.Nullable;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public record UpdateNewsDtoRequest(
        @Nullable
        @Size(min = 5, max = 30, message = "News title length should be between from 5 to 30 chars")
        String title,
        @Nullable
        @Size(min = 5, max = 255, message = "News content length should be between from 5 to 255 chars")
        String content,
        @Nullable
        String author,
        @Nullable
        List<String> tags,
        @Nullable
        List<Long> commentsIds
) {
    public UpdateNewsDtoRequest {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        if (commentsIds == null) {
            commentsIds = new ArrayList<>();
        }
    }
}
