package com.mjc.school.repository.filter;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Objects;

public class EntitySpecification<T> implements Specification<T> {
    private final SearchCriteria searchCriteria;

    public EntitySpecification(SearchCriteria searchCriteria){
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        String strToSearch = searchCriteria.getValue().toString().toLowerCase();

        return switch (Objects.requireNonNull(SearchOperation.getSimpleOperation(searchCriteria.getOperation()))) {
            case EQUAL -> cb.equal(cb.lower(root.get(searchCriteria.getFilterKey())), strToSearch);
            case CONTAINS ->
                    cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");
            case DOES_NOT_CONTAIN ->
                    cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch + "%");
            case NOT_EQUAL -> cb.notEqual(cb.lower(root.get(searchCriteria.getFilterKey())), strToSearch);
            case BEGINS_WITH -> cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), strToSearch + "%");
            case DOES_NOT_BEGIN_WITH -> cb.notLike(root.get(searchCriteria.getFilterKey()), strToSearch + "%");
            case ENDS_WITH -> cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch);
            case DOES_NOT_END_WITH ->
                    cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), "%" + strToSearch);
            default -> null;
        };
    }

}
