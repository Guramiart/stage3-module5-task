package com.mjc.school.repository.impl;

import com.mjc.school.repository.AbstractRepository;
import com.mjc.school.repository.model.News;
import org.springframework.stereotype.Repository;

@Repository
public class NewsRepository extends AbstractRepository<News, Long> {
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
