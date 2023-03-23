package com.mjc.school.repository.impl;

import com.mjc.school.repository.AbstractRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.query.SearchQueryParam;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepository extends AbstractRepository<Author, Long> {

    @Override
    public List<Predicate> getPredicates(CriteriaBuilder criteriaBuilder, Root<Author> root, SearchQueryParam request) {
        List<Predicate> predicates = new ArrayList<>();
        if(request.getName() != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
        }
        return predicates;
    }

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
