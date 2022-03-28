package com.vr.authorizator.web.controller.exceptionhandler;

import com.vr.authorizator.domain.exception.CardNotExistsException;
import com.vr.authorizator.domain.exception.InsufficientBalanceException;
import com.vr.authorizator.domain.exception.InvalidPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class TransactionControllerExceptionHandler {

    @ExceptionHandler({InvalidPasswordException.class, InsufficientBalanceException.class, CardNotExistsException.class})
    public ResponseEntity<String> handleCreateTransactionException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
