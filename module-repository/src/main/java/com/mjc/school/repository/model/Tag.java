package com.mjc.school.repository.model;

import com.mjc.school.repository.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tags")
public class Tag implements BaseEntity<Long> {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private List<News> news;

    private static class Builder {
        private final Long id;
        private final String name;
        private List<News> news;

        public Builder(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Builder news(List<News> news) {
            this.news = news;
            return this;
        }

        public Tag build() {
            return new Tag(this);
        }
    }
    public Tag() {}

    public Tag(Builder builder) {
        id = builder.id;
        name = builder.name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag model = (Tag) o;
        return Objects.equals(id, model.id)
                && Objects.equals(name, model.name)
                && Objects.equals(news, model.news);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, news);
    }

    @Override
    public String toString() {
        return String.format("%s[id=%s, name=%s]", getClass().getSimpleName(), id, name);
    }

}
