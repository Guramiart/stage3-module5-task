package com.mjc.school.repository.impl;

import com.mjc.school.repository.AbstractRepository;
import com.mjc.school.repository.model.Author;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
public class AuthorRepository extends AbstractRepository<Author, Long> {

    @Override
    protected void updateEntity(Author prevState, Author nextState) {
        if(nextState.getName() != null && !nextState.getName().isBlank()) {
            prevState.setName(nextState.getName());
        }
    }

    public Optional<Author> readByName(String name) {
        TypedQuery<Author> query = em.createQuery(
                "SELECT a FROM Author a WHERE a.name = :name", Author.class
        ).setParameter("name", name);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public Optional<Author> readByNewsId(Long newsId) {
        TypedQuery<Author> query = em.createQuery(
                "SELECT a FROM Author a INNER JOIN a.news n WHERE n.id =: newsId", Author.class
        ).setParameter("newsId", newsId);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

}
