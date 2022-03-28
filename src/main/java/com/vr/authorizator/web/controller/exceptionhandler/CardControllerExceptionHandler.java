package com.vr.authorizator.web.controller.exceptionhandler;

import com.vr.authorizator.domain.exception.CardAlredyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CardControllerExceptionHandler {

    @ExceptionHandler(CardAlredyExistsException.class)
    public ResponseEntity<Object> handleCreateCardException(CardAlredyExistsException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getRequest(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
