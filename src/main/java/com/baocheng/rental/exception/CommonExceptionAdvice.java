package com.baocheng.rental.exception;

import com.baocheng.rental.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class CommonExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleError(MethodArgumentNotValidException e){
        log.warn("Method Argument Not Valid",e);
        BindingResult bindingResult = e.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        String message = fieldError.getField() + fieldError.getDefaultMessage();
        return Response.builder().success(false).code(400).message(message).build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Response handleConstraintViolationException(ConstraintViolationException e){
        log.warn("Method Argument Not Valid. ConstraintViolationException",e);
        return Response.builder().success(false).code(400).message(e.getMessage()).build();
    }




}