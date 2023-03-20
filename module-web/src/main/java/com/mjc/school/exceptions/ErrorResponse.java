package com.mjc.school.exceptions;

public record ErrorResponse(String code, String message, String details) {
}
