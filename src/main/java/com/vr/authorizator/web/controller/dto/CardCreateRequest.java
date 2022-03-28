package com.vr.authorizator.web.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardCreateRequest {

    @NotBlank
    @JsonProperty("numeroCartao")
    private String cardNumber;

    @NotBlank
    @JsonProperty("senha")
    private String password;

}
