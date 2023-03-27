package com.mjc.school.service.dto.query;

import org.springframework.lang.Nullable;

import java.util.List;

public class NewsSearchDtoRequest extends SortDtoRequest {

    private final String title;
    private final String content;
    private final String author;
    private final List<String> tags;
    private final List<Integer> tagsIds;

    public NewsSearchDtoRequest(@Nullable int page, @Nullable int size, @Nullable String sort,
                                @Nullable String title, @Nullable String content, @Nullable String author,
                                @Nullable List<String> tags, @Nullable List<Integer> tagsIds) {
        super(page, size, sort);
        this.title = title;
        this.content = content;
        this.author = author;
        this.tags = tags;
        this.tagsIds = tagsIds;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<Integer> getTagsIds() {
        return tagsIds;
    }
}
