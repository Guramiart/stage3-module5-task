package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {

    List<TagDtoResponse> modelListToDto(List<Tag> modelList);

    TagDtoResponse modelToDto(Tag model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "news", ignore = true)
    Tag dtoToModel(TagDtoRequest dto);

}
