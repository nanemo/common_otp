package com.poject.common.exception;


import com.poject.common.model.dto.ValidationResult;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger("businessLogger");

    @Autowired
    public GlobalExceptionHandler() {

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleExceptions(Exception e) {
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ValidationResult.internalError());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleExceptions(HttpMessageNotReadableException e) {
        LOGGER.error(ExceptionUtils.getStackTrace(e));
        return ResponseEntity.status(BAD_REQUEST).body(ValidationResult.badRequest());
    }
}


