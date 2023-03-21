package com.mjc.school.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public abstract class AbstractRepository<T extends BaseEntity<K>, K> implements BaseRepository<T, K> {

    @PersistenceContext
    protected EntityManager em;
    private final Class<T> entity;

    protected AbstractRepository() {
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        entity = (Class<T>) type.getActualTypeArguments()[0];
    }

    @Override
    public Page<T> readAll(Pageable pageable) {
        Query query = em.createQuery("SELECT e FROM " + entity.getSimpleName() + " e");
        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);
        List<T> modelList = query.getResultList();
        Query queryCount = em.createQuery("SELECT COUNT(*) FROM " + entity.getSimpleName());
        long count = (long) queryCount.getSingleResult();
        return new PageImpl<>(modelList, pageable, count);
    }

    @Override
    public Optional<T> readById(K id) {
        return Optional.ofNullable(em.find(entity, id));
    }

    @Override
    public T create(T entity) {
        em.persist(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        return readById(entity.getId())
                .map(e -> {
                    updateEntity(e, entity);
                    T updated = em.merge(e);
                    em.flush();
                    return updated;
                })
                .orElse(null);
    }

    protected abstract void updateEntity(T prevState, T nextState);

    @Override
    public boolean deleteById(K id) {
        if(id != null) {
            em.remove(getReference(id));
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(K id) {
        Query query = em
                .createQuery("SELECT COUNT (*) FROM " + entity.getSimpleName() + " e WHERE e.id = ?1")
                .setParameter(1, id);
        return (Long) query.getSingleResult() > 0;
    }

    @Override
    public T getReference(K id) {
        return em.getReference(entity, id);
    }

    @Override
    @Transactional
    public List<T> readBySearchCriteria(Specification<Object> specification) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entity);
        final Root root = criteriaQuery.from(entity);
        if (specification != null) {
            Predicate predicate = specification.toPredicate(root, criteriaQuery, criteriaBuilder);
            criteriaQuery.where(predicate);
        }
        TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
