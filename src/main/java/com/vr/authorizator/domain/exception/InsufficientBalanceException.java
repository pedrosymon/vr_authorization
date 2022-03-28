package com.vr.authorizator.domain.exception;

public class InsufficientBalanceException extends Exception{
    public InsufficientBalanceException(){
        super("SALDO_INSUFICIENTE");
    }
}
