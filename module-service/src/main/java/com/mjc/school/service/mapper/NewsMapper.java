package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;

public abstract class NewsMapper implements BaseMapper<NewsDtoRequest, NewsDtoResponse, News> {
}
