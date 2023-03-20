package com.mjc.school.repository.impl;

import com.mjc.school.repository.AbstractRepository;
import com.mjc.school.repository.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CommentRepository extends AbstractRepository<Comment, Long> {
    @Override
    protected void updateEntity(Comment prevState, Comment nextState) {
        if(nextState.getContent() != null && !nextState.getContent().isBlank()) {
            prevState.setContent(nextState.getContent());
        }
        if(nextState.getNews() != null) {
            prevState.setNews(nextState.getNews());
        }
    }

    public List<Comment> readByNewsId(Long newsId) {
        TypedQuery<Comment> query = em
                .createQuery("SELECT c FROM Comment c INNER JOIN c.news n WHERE n.id = :newsId", Comment.class)
                .setParameter("newsId", newsId);
        return query.getResultList();
    }

}
