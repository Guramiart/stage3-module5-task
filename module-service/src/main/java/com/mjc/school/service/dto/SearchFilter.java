package com.mjc.school.service.dto;

import com.mjc.school.repository.filter.SearchCriteria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public record SearchFilter(Pageable pageable, List<SearchCriteria> searchCriteria) {
}
