package com.vr.authorizator.domain.exception;

public class InvalidPasswordException extends Exception{

    public InvalidPasswordException(){
        super("SENHA_INVALIDA");
    }
}
