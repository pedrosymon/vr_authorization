package com.vr.authorizator.domain.exception;

import com.vr.authorizator.web.controller.dto.CardCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardAlredyExistsException extends Exception{
    private CardCreateRequest request;
}
