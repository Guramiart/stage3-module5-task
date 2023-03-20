package com.mjc.school.service.exceptions;

public enum ServiceErrorCode {
    NEWS_ID_DOES_NOT_EXIST("000001", "News with id %d does not exist."),
    AUTHOR_ID_DOES_NOT_EXIST("000002", "Author with id %d does not exist."),
    TAG_ID_DOES_NOT_EXIST("000003", "Tag with id %d does not exist."),
    COMMENT_ID_DOES_NOT_EXIST("000004", "Comment with id %d does not exist."),
    RESOURCE_NOT_FOUND("000010", "Resource not found");

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
