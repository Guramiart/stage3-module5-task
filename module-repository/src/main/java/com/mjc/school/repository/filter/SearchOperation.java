package com.mjc.school.repository.filter;

public enum SearchOperation {

    CONTAINS, DOES_NOT_CONTAIN, EQUAL, NOT_EQUAL,
    BEGINS_WITH, DOES_NOT_BEGIN_WITH, ENDS_WITH, DOES_NOT_END_WITH,
    NULL, ANY, ALL;

    public static final String[] SIMPLE_OPERATION_SET = { "eq", "cn", "nc", "ne", "bw", "bn", "ew", "en" };

    public static SearchOperation getDataOption(String dataOption){
        return switch (dataOption) {
            case "all" -> ALL;
            case "any" -> ANY;
            default -> null;
        };
    }

    public static SearchOperation getSimpleOperation(String input) {
        return switch (input) {
            case "eq" -> EQUAL;
            case "cn" -> CONTAINS;
            case "nc" -> DOES_NOT_CONTAIN;
            case "ne" -> NOT_EQUAL;
            case "bw" -> BEGINS_WITH;
            case "bn" -> DOES_NOT_BEGIN_WITH;
            case "ew" -> ENDS_WITH;
            case "en" -> DOES_NOT_END_WITH;
            default -> NULL;
        };
    }

}
