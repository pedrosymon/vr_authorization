package com.vr.authorizator.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vr.authorizator.domain.model.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardCreateResponse {

    @JsonProperty("numeroCartao")
    private String cardNumber;

    @JsonProperty("senha")
    private String password;

    public CardCreateResponse(Card card) {
        this.cardNumber = card.getCardNumber();
        this.password = card.getPassword();
    }
}
