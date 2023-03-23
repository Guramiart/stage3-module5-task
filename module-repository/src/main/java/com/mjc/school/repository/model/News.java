package com.mjc.school.repository.model;

import com.mjc.school.repository.BaseEntity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.EntityListeners;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "news")
@EntityListeners(AuditingEntityListener.class)
public class News implements BaseEntity<Long> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", unique = true)
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "last_updated_date")
    @LastModifiedDate
    private LocalDateTime lastUpdatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(
            name = "news_tags",
            joinColumns = { @JoinColumn(name = "news_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") }
    )
    private List<Tag> tags;

    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    public static class Builder {
        private final Long id;
        private final String title;
        private final String content;
        private LocalDateTime createdDate;
        private LocalDateTime lastUpdatedDate;
        private Author author;
        private List<Tag> tags;
        private List<Comment> comments;

        public Builder(Long id, String title, String content) {
            this.id = id;
            this.title = title;
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

        public Builder author(Author author) {
            this.author = author;
            return this;
        }

        public Builder tags(List<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public Builder comments(List<Comment> comments) {
            this.comments = comments;
            return this;
        }

        public News build() {
            return new News(this);
        }
    }

    public News() {}

    public News(Builder builder) {
        id = builder.id;
        title = builder.title;
        content = builder.content;
        createdDate = builder.createdDate;
        lastUpdatedDate = builder.lastUpdatedDate;
        author = builder.author;
        tags = builder.tags;
        comments = builder.comments;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
        tag.getNews().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News model = (News) o;
        return Objects.equals(id, model.id)
                && Objects.equals(title, model.title)
                && Objects.equals(content, model.content)
                && Objects.equals(createdDate, model.createdDate)
                && Objects.equals(lastUpdatedDate, model.lastUpdatedDate)
                && Objects.equals(author, model.author)
                && Objects.equals(tags, model.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, createdDate, lastUpdatedDate, author, tags);
    }

    @Override
    public String toString() {
        return String.format("%s[id=%d, title=%s, content=%s, createDate=%s, updateDate=%s, authorId=%d]",
                getClass().getSimpleName(), id, title, content, createdDate, lastUpdatedDate, author.getId());
    }
}
