package com.vr.authorizator.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vr.authorizator.domain.model.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    @JsonProperty("numeroCartao")
    private String cardNumber;

    @JsonProperty("senha")
    private String password;

    @JsonProperty("valor")
    private Double amount;

    public TransactionResponse(Transaction transaction){
        this.cardNumber = transaction.getCard().getCardNumber();
        this.password = transaction.getCard().getPassword();
        this.amount = transaction.getAmount();
    }
}
