package com.mjc.school.service.exceptions;

public enum ServiceErrorCode {
    NEWS_ID_DOES_NOT_EXIST("000001", "News with id %d does not exist."),
    AUTHOR_ID_DOES_NOT_EXIST("000002", "Author with id %d does not exist."),
    AUTHOR_DOES_NOT_EXIST_FOR_NEWS_ID("000003", "Author not found for news with id %d."),
    TAG_ID_DOES_NOT_EXIST("000004", "Tag with id %d does not exist."),
    TAG_DOES_NOT_EXIST_FOR_NEWS_ID("000005", "Tags not found for news with id %d."),
    COMMENT_ID_DOES_NOT_EXIST("000006", "Comment with id %d does not exist."),
    COMMENT_DOES_NOT_EXIST_FOR_NEWS_ID("000007", "Comments not found for news with id %d."),
    VALIDATION_ERROR("00008", "Validation error"),
    RESOURCE_NOT_FOUND("000010", "Resource not found"),
    NOT_SUPPORTED("000100", "Operation not supported");

    private final String errorCode;
    private final String errorMessage;
    private ServiceErrorCode(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = "ERROR_CODE: " + errorCode + " ERROR_MESSAGE: " + errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
