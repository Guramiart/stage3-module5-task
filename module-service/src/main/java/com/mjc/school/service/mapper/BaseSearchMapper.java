package com.mjc.school.service.mapper;

import com.mjc.school.repository.filter.SearchCriteria;
import com.mjc.school.repository.filter.SearchOperation;
import com.mjc.school.service.dto.SearchFilter;
import com.mjc.school.service.dto.SearchFilterDtoRequest;

import java.util.ArrayList;
import java.util.List;

public class BaseSearchMapper {

    public SearchFilter mapSearchCriteria(SearchFilterDtoRequest searchFilterDtoRequest) {
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();

        for (String filter : searchFilterDtoRequest.searchCriteria()) {
            String[] splitFilter = filter.split(":");
            if (splitFilter.length == 3) {
                searchCriteriaList.add(
                        new SearchCriteria(splitFilter[0],
                                SearchOperation.getSimpleOperation(splitFilter[1]).name(), splitFilter[2]));
            }
        }
        return new SearchFilter(searchCriteriaList);
    }
}
