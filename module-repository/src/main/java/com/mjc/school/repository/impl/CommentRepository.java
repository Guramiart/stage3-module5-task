package com.mjc.school.repository.impl;

import com.mjc.school.repository.AbstractRepository;
import com.mjc.school.repository.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepository extends AbstractRepository<Comment, Long> {
    @Override
    protected void updateEntity(Comment prevState, Comment nextState) {
        prevState.setContent(nextState.getContent());
        prevState.setNews(nextState.getNews());
    }

}
