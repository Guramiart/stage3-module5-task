package com.mjc.school.service;

import com.mjc.school.repository.BaseEntity;
import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.filter.EntitySpecification;
import com.mjc.school.repository.filter.EntitySpecificationBuilder;
import com.mjc.school.service.dto.SearchFilter;
import com.mjc.school.service.dto.SearchFilterDtoRequest;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mapper.BaseSearchMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractService<T, R, K, M extends BaseEntity<K>, U> implements BaseService<T, R, K, U> {

    private final BaseRepository<M, K> repository;
    private BaseSearchMapper searchMapper;

    protected AbstractService(BaseRepository<M, K> repository) {
        this.repository = repository;
    }

    protected abstract String getErrorMessage();
    protected abstract List<R> modelListToDto(List<M> modelList);
    protected abstract R modelToDto(M model);
    protected abstract M dtoToModel(T dto);
    protected abstract M updateDtoToModel(U dto);

    @Override
    @Transactional(readOnly = true)
    public List<R> readAll(Pageable pageable) {
        return modelListToDto(repository.readAll(pageable).getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public R readById(K id) {
        return repository.readById(id)
                .map(this::modelToDto)
                .orElseThrow(() -> {
                    throw new NotFoundException(String.format(getErrorMessage(), id));
                });
    }

    @Override
    @Transactional
    public R create(T createRequest) {
        M model = repository.create(dtoToModel(createRequest));
        return modelToDto(model);
    }

    @Override
    @Transactional
    public R update(K id, U updateRequest) {
        if(repository.existById(id)) {
            M model = updateDtoToModel(updateRequest);
            model.setId(id);
            return modelToDto(repository.update(model));
        } else {
            throw new NotFoundException(String.format(getErrorMessage(), id));
        }
    }

    @Override
    @Transactional
    public void deleteById(K id) {
        if(repository.existById(id)) {
            repository.deleteById(id);
        } else {
            throw new NotFoundException(String.format(getErrorMessage(), id));
        }
    }

    @Override
    @Transactional
    public void readBySearchCriteria(SearchFilterDtoRequest searchFilterDtoRequest) {
        SearchFilter searchFilter = searchMapper.mapSearchCriteria(searchFilterDtoRequest);
        Specification<Object> specification = new EntitySpecificationBuilder<>()
                .with(searchFilter.searchCriteria())
                .build();
        repository.readBySearchCriteria(specification);
    }
}
