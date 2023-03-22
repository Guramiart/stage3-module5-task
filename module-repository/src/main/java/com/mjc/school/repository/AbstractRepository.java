package com.mjc.school.repository;

import com.mjc.school.repository.filter.EntityRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
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
    public Page<T> readAll(EntityRequest entityRequest) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entity);
        final Root<T> root = criteriaQuery.from(entity);
        if (entityRequest.searchSpecification() != null) {
            Predicate predicate = entityRequest.searchSpecification().toPredicate(root, criteriaQuery, criteriaBuilder);
            criteriaQuery.where(predicate);
        }
        TypedQuery<T> typedQuery = em.createQuery(criteriaQuery);
        int page = entityRequest.pageable().getPageNumber();
        int size = entityRequest.pageable().getPageSize();
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);

        return new PageImpl<T>(typedQuery.getResultList(), entityRequest.pageable(), size);
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
