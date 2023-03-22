package com.mjc.school.repository.filter;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EntitySpecificationBuilder<T> {

    private final List<SearchCriteria> params;

    public EntitySpecificationBuilder() {
        params = new ArrayList<>();
    }

    public final EntitySpecificationBuilder<T> with(String key, String operation, Object value){
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public final EntitySpecificationBuilder<T> with(List<SearchCriteria> searchCriteria){
        params.addAll(searchCriteria);
        return this;
    }

    public Specification<T> build(){
        if(params.size() == 0){
            return null;
        }

        Specification<T> result = new EntitySpecification<>(params.get(0));
        for (int i = 1; i < params.size(); i++){
            SearchCriteria criteria = params.get(i);
            result =  SearchOperation.getDataOption(criteria.getDataOption()).equals(SearchOperation.ALL)
                    ? Specification.where(result).and(new EntitySpecification<>(criteria))
                    : Specification.where(result).or(new EntitySpecification<>(criteria));
        }
        return result;
    }
}
