package com.mjc.school.service.dto;

import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SearchFilterDtoRequest {

    private int page = 1;
    private int size = 5;
    private List<String> searchCriteria = new ArrayList<>();
    private String sortBy = "createdDate:ASC";

    public SearchFilterDtoRequest(
            @Nullable int page,
            @Nullable int size,
            @Nullable List<String> searchCriteria,
            @Nullable String sortBy) {
        if(page > 0) {
            this.page = page;
        }
        if(size > 0) {
            this.size = size;
        }
        if(searchCriteria != null) {
            this.searchCriteria = searchCriteria;
        }
        if(sortBy != null) {
            this.sortBy = sortBy;
        }
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public List<String> getSearchCriteria() {
        return searchCriteria;
    }

    public String getSortBy() {
        return sortBy;
    }
}
