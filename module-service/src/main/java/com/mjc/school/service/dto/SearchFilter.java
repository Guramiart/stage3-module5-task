package com.mjc.school.service.dto;

import com.mjc.school.repository.filter.SearchCriteria;

import java.util.List;

public record SearchFilter(List<SearchCriteria> searchCriteria) {
}
