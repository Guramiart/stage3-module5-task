package com.mjc.school.repository.impl;

import com.mjc.school.repository.AbstractRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.repository.query.SearchQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NewsRepository extends AbstractRepository<News, Long> {

    @Override
    protected List<Predicate> getPredicates(CriteriaBuilder criteriaBuilder, Root<News> root, SearchQueryParam request) {
        List<Predicate> predicates = new ArrayList<>();

        if(request.getTagsIds() != null || request.getTags() != null) {
            Join<Tag, News> newsTagJoin = root.join("tags");
            if(request.getTagsIds() != null) {
                predicates.add(newsTagJoin.get("id").in(request.getTagsIds()));
            }
            if(request.getTags() != null) {
                predicates.add(newsTagJoin.get("name").in(request.getTagsIds()));
            }
        }

        if(request.getAuthor() != null) {
            Join<Author, News> newsAuthorJoin = root.join("author");
            predicates.add(criteriaBuilder.like(newsAuthorJoin.get("name"), "%" + request.getAuthor() + "%"));
        }

        if(request.getTitle() != null) {
            predicates.add(criteriaBuilder.like(root.get("title"), "%" + request.getTitle() + "%"));
        }

        if(request.getContent() != null) {
            predicates.add(criteriaBuilder.like(root.get("content"), "%" + request.getContent() + "%"));
        }
        return predicates;
    }

    @Override
    protected void updateEntity(News prevState, News nextState) {
        if(nextState.getTitle() != null && !nextState.getTitle().isBlank()) {
            prevState.setTitle(nextState.getTitle());
        }
        if(nextState.getContent() != null && !nextState.getContent().isBlank()) {
            prevState.setContent(nextState.getContent());
        }
        if(nextState.getAuthor() != null && !nextState.getAuthor().getName().isBlank()) {
            prevState.setAuthor(nextState.getAuthor());
        }
        if(nextState.getTags() != null && !nextState.getTags().isEmpty()) {
            prevState.setTags(nextState.getTags());
        }
        if(nextState.getComments() != null && !nextState.getComments().isEmpty()) {
            prevState.setComments(nextState.getComments());
        }
    }

}
