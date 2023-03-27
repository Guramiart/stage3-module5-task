package com.mjc.school.repository;

import com.mjc.school.repository.exception.SortOperationException;
import com.mjc.school.repository.query.SearchQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Objects;
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
   public Page<T> readAll(SearchQueryParam entityRequest) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entity);
        final Root<T> root = criteriaQuery.from(entity);
        List<Predicate> predicates = getPredicates(criteriaBuilder, root, entityRequest);
        if(entityRequest.getSortBy() != null) {
            mapSorting(entityRequest, criteriaQuery, criteriaBuilder, root);
        }
        criteriaQuery.select(root).distinct(true).where(predicates.toArray(new Predicate[0]));
        TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);
        Pageable pageable = entityRequest.getPageable();
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);

        return new PageImpl<T>(typedQuery.getResultList(), pageable, size);
    }

    protected abstract List<Predicate> getPredicates(CriteriaBuilder criteriaBuilder, Root<T> root, SearchQueryParam request);

    private void mapSorting(SearchQueryParam request, CriteriaQuery<T> criteriaQuery, CriteriaBuilder criteriaBuilder, Root<T> root) {
        try {
            if(Objects.equals(request.getOrder(), "asc")) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(request.getSortBy())));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(request.getSortBy())));
            }
        } catch (IllegalArgumentException ex) {
            throw new SortOperationException("Sorting by " + request.getSortBy() + " is not supported");
        }
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

}
