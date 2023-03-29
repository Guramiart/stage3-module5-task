package com.mjc.school.service.dto.response;

import java.time.LocalDateTime;

public class CommentDtoResponse extends ResponseEntity<Long> {
    private final String content;
    private final Long newsId;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastUpdatedDate;

    public CommentDtoResponse(
            Long id,
            String content,
            Long newsId,
            LocalDateTime createdDate,
            LocalDateTime lastUpdatedDate
    ) {
        super(id);
        this.content = content;
        this.newsId = newsId;
        this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getContent() {
        return content;
    }

    public Long getNewsId() {
        return newsId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

}
