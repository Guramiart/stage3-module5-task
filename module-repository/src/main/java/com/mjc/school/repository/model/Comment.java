package com.mjc.school.repository.model;

import com.mjc.school.repository.BaseEntity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
public class Comment implements BaseEntity<Long> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date")
    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "news_id")
    private News news;

    public static class Builder {

        private final Long id;
        private final String content;
        private LocalDateTime createdDate;
        private LocalDateTime lastUpdatedDate;
        private News news;

        public Builder(Long id, String content) {
            this.id = id;
            this.content = content;
        }

        public Builder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public Builder lastUpdatedDate(LocalDateTime lastUpdatedDate) {
            this.lastUpdatedDate = lastUpdatedDate;
            return this;
        }

        public Builder news(News news) {
            this.news = news;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }

    }

    public Comment(){}

    public Comment(Builder builder) {
        id = builder.id;
        content = builder.content;
        createdDate = builder.createdDate;
        lastUpdatedDate = builder.lastUpdatedDate;
        news = builder.news;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment model = (Comment) o;
        return Objects.equals(id, model.id)
                && Objects.equals(content, model.content)
                && Objects.equals(createdDate, model.createdDate)
                && Objects.equals(lastUpdatedDate, model.lastUpdatedDate)
                && Objects.equals(news, model.news);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, createdDate, lastUpdatedDate, news);
    }

    @Override
    public String toString() {
        return String.format("%s[id=%s, comment=%s, createdDate=%s, lastUpdatedDate=%s, newsId=%d]",
                getClass().getSimpleName(), id, content, createdDate, lastUpdatedDate, news.getId());
    }
}
