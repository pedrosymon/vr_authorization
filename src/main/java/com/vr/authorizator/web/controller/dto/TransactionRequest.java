package com.vr.authorizator.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {

    @JsonProperty("numeroCartao")
    private String cardNumber;

    @JsonProperty("senha")
    private String password;

    @JsonProperty("valor")
    private Double amount;

}
