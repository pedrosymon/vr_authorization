package com.vr.authorizator.domain.exception;

public class CardNotExistsException extends Exception{
    public CardNotExistsException(){
        super("CARTAO_INEXISTENTE");
    }
}
