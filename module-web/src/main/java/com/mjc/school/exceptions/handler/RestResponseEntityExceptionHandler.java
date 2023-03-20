package com.mjc.school.exceptions.handler;

import com.mjc.school.exceptions.ErrorResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ NotFoundException.class })
    protected ResponseEntity<Object> handleConflict(NotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(
                ServiceErrorCode.RESOURCE_NOT_FOUND.getErrorCode(),
                ServiceErrorCode.RESOURCE_NOT_FOUND.getErrorMessage(),
                ex.getMessage()
        ), HttpStatus.NOT_FOUND);
    }
}
