package com.mjc.school.service.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public record CreateNewsDtoRequest(
        @NotNull
        @Size(min = 5, max = 30, message = "News title length should be between from 5 to 30 chars")
        String title,

        @NotNull
        @Size(min = 5, max = 255, message = "News content length should be between from 5 to 255 chars")
        String content,

        @NotNull
        @Size(min = 3, max = 15, message = "Author name length should be between from 3 to 15 chars")
        String author,

        List<String> tags,
        List<Long> commentsIds
) {
        public CreateNewsDtoRequest {
                if(tags == null) {
                        tags = new ArrayList<>();
                }
                if(commentsIds == null) {
                        commentsIds = new ArrayList<>();
                }
        }
}
