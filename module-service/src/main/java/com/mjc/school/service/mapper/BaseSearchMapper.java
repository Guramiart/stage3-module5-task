package com.mjc.school.service.mapper;

import com.mjc.school.repository.filter.SearchCriteria;
import com.mjc.school.repository.filter.SearchOperation;
import com.mjc.school.service.dto.SearchFilter;
import com.mjc.school.service.dto.SearchFilterDtoRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class BaseSearchMapper {

    public static final String BASE_SEARCH_DELIMITER = ":";

    public SearchFilter mapSearchCriteria(SearchFilterDtoRequest searchFilterDtoRequest) {
        Pageable pageable = PageRequest.of(searchFilterDtoRequest.getPage(), searchFilterDtoRequest.getSize());

        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        if (CollectionUtils.isEmpty(searchFilterDtoRequest.getSearchCriteria())) {
            return new SearchFilter(pageable, searchCriteriaList);
        }

        for (String filter : searchFilterDtoRequest.getSearchCriteria()) {
            String[] splitFilter = filter.split(BASE_SEARCH_DELIMITER);
            if (splitFilter.length == 3) {
                searchCriteriaList.add(new SearchCriteria(splitFilter[0], splitFilter[1], splitFilter[2]));
            }
        }

        return new SearchFilter(pageable, searchCriteriaList);
    }

}
