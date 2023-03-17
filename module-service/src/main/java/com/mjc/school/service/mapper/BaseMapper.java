package com.mjc.school.service.mapper;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BaseMapper<T, R, M> {

    List<R> modelListToDto(List<M> modelList);

    R modelToDto(M model);

    M dtoToModel(T dto);
}
