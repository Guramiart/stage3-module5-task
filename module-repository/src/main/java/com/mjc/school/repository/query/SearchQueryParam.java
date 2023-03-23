package com.mjc.school.repository.query;

import org.springframework.data.domain.Pageable;

import java.util.List;

public class SearchQueryParam {

    private Pageable pageable;
    private String title;
    private String content;
    private String name;
    private String author;
    private List<String> tags;
    private List<Integer> tagsIds;

    public static class Builder {
        private final Pageable pageable;
        private String title;
        private String content;
        private String name;
        private String author;
        private List<String> tags;
        private List<Integer> tagsIds;

        public Builder(Pageable pageable) {
            this.pageable = pageable;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder tagsIds(List<Integer> tagsIds) {
            this.tagsIds = tagsIds;
            return this;
        }

        public SearchQueryParam build() {
            return new SearchQueryParam(this);
        }
    }

    public SearchQueryParam() {}

    public SearchQueryParam(Builder builder) {
        pageable = builder.pageable;
        title = builder.title;
        content = builder.content;
        author = builder.author;
        name = builder.name;
        tags = builder.tags;
        tagsIds = builder.tagsIds;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
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
