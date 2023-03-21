package com.mjc.school.repository.filter;

public class SearchCriteria {

    private String filterKey;
    private Object value;
    private String operation;
    private String dataOption;

    public SearchCriteria(String filterKey, String operation, Object value) {
        this.filterKey = filterKey;
        this.operation = operation;
        this.value = value;
    }

    public String getFilterKey() {
        return filterKey;
    }

    public Object getValue() {
        return value;
    }

    public String getOperation() {
        return operation;
    }

    public String getDataOption() {
        return dataOption;
    }

}
