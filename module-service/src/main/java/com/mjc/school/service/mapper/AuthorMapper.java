package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.Author;
import com.mjc.school.service.dto.AuthorDtoRequest;
import com.mjc.school.service.dto.AuthorDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper extends BaseMapper<AuthorDtoRequest, AuthorDtoResponse, Author> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "news", ignore = true)
    Author dtoToModel(AuthorDtoRequest dto);

}
