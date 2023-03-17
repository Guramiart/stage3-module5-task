package com.mjc.school.service;

import com.mjc.school.repository.BaseEntity;
import com.mjc.school.repository.BaseRepository;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.mapper.BaseMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class AbstractService<T, R, K, L extends BaseRepository<M, K>, M extends BaseEntity<K>, N extends BaseMapper<T, R, M>>
        implements BaseService<T, R, K> {

    private final L repository;
    private final N mapper;

    protected AbstractService(L repository, N mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    protected abstract String getErrorMessage();

    @Override
    @Transactional(readOnly = true)
    public List<R> readAll(Pageable pageable) {
        return mapper.modelListToDto(repository.readAll(pageable).getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public R readById(K id) {
        repository.readById(id)
                .map(mapper::modelToDto)
                .orElseThrow(() -> {
                    throw new NotFoundException(String.format(getErrorMessage(), id));
                });
        return null;
    }

    @Override
    @Transactional
    public R create(T createRequest) {
        M model = repository.create(mapper.dtoToModel(createRequest));
        return mapper.modelToDto(model);
    }

    @Override
    @Transactional
    public R update(K id, T updateRequest) {
        if(repository.existById(id)) {
            M model = mapper.dtoToModel(updateRequest);
            model.setId(id);
            return mapper.modelToDto(repository.update(model));
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
}
