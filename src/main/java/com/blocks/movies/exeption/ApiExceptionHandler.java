package com.blocks.movies.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FieldException.class)
    public ResponseEntity<Object> handleFieldException(FieldException fieldException) {
        ExceptionSchema exception = new ExceptionSchema(fieldException.getField(), fieldException.getMessage(), fieldException.getRejectedValue());
        return new ResponseEntity(exception, HttpStatus.BAD_REQUEST);
    }
}
