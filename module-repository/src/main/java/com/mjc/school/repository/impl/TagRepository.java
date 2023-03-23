package com.mjc.school.repository.impl;

import com.mjc.school.repository.AbstractRepository;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.repository.query.SearchQueryParam;
import org.springframework.data.domain.Page;
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
public class TagRepository extends AbstractRepository<Tag, Long> {

    @Override
    protected List<Predicate> getPredicates(CriteriaBuilder criteriaBuilder, Root<Tag> root, SearchQueryParam request) {
        List<Predicate> predicates = new ArrayList<>();

        if(request.getName() != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
        }
        return predicates;
    }

    @Override
    protected void updateEntity(Tag prevState, Tag nextState) {
        if(nextState.getName() != null && !nextState.getName().isBlank()) {
            prevState.setName(nextState.getName());
        }
        if(nextState.getNews() != null && !nextState.getNews().isEmpty()) {
            prevState.setNews(nextState.getNews());
        }
    }

    public Optional<Tag> readByName(String name) {
        TypedQuery<Tag> query = em.createQuery(
                "SELECT t FROM Tag t WHERE t.name = :name", Tag.class
        ).setParameter("name", name);
        try {
            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    public List<Tag> readByNewsId(Long newsId) {
        TypedQuery<Tag> query = em.createQuery(
                "SELECT t FROM Tag t INNER JOIN t.news n WHERE n.id = :newsId", Tag.class
        ).setParameter("newsId", newsId);
        return query.getResultList();
    }

}
