package com.mjc.school.repository.filter;

public class SearchCriteria {

    private final String filterKey;
    private final Object value;
    private final String operation;
    private String dataOption = "all";

    public SearchCriteria(String filterKey, String operation, Object value) {
        this.filterKey = filterKey;
        this.operation = operation;
        this.value = value;
    }

    public SearchCriteria(String filterKey, String operation, Object value, String dataOption) {
        this(filterKey, operation, value);
        this.dataOption = dataOption;
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
