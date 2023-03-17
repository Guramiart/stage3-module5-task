package com.mjc.school.repository.impl;

import com.mjc.school.repository.AbstractRepository;
import com.mjc.school.repository.model.News;
import org.springframework.stereotype.Repository;

@Repository
public class NewsRepository extends AbstractRepository<News, Long> {
    @Override
    protected void updateEntity(News prevState, News nextState) {
        prevState.setTitle(nextState.getTitle());
        prevState.setContent(nextState.getContent());
        prevState.setAuthor(nextState.getAuthor());
        prevState.setTags(nextState.getTags());
        prevState.setComments(nextState.getComments());
    }

}
