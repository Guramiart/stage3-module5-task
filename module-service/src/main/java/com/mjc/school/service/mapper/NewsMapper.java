package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class NewsMapper implements BaseMapper<NewsDtoRequest, NewsDtoResponse, News> {
}
