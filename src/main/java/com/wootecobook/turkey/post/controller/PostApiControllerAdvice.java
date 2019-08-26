package com.wootecobook.turkey.post.controller;

import com.wootecobook.turkey.commons.ErrorMessage;
import com.wootecobook.turkey.post.service.exception.NotPostOwnerException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice(basePackageClasses = {PostApiController.class})
public class PostApiControllerAdvice {

    @ExceptionHandler({EntityNotFoundException.class, NotPostOwnerException.class})
    protected ResponseEntity<ErrorMessage> handleException(RuntimeException exception) {
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());

        return ResponseEntity.badRequest().body(errorMessage);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorMessage> handleBindException(BindException bindException) {
        ErrorMessage errorMessage = new ErrorMessage(bindException.getFieldError().getDefaultMessage());

        return ResponseEntity.badRequest().body(errorMessage);
    }
}