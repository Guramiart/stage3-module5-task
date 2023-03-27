package com.mjc.school.service.dto.query;

import org.springframework.lang.Nullable;

public class SortDtoRequest extends PageDtoRequest{

    private static final String SORT_DELIMITER = ":";
    private String sortBy = "createdDate";
    private String order = "desc";

    public SortDtoRequest(@Nullable int page, @Nullable int size, @Nullable String sort) {
        super(page, size);
        if(sort != null) {
            String[] sortParams = sort.split(SORT_DELIMITER);
            sortBy = sortParams[0];
            if(sortParams.length == 2) {
                order = sortParams[1];
            }
        }
    }

    public String getSortBy() {
        return sortBy;
    }

    public String getOrder() {
        return order;
    }
}
