package com.vr.authorizator.web.controller;

import com.vr.authorizator.domain.service.TransactionService;
import com.vr.authorizator.web.controller.dto.TransactionRequest;
import com.vr.authorizator.web.controller.dto.TransactionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/transacoes")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping()
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody @Valid final TransactionRequest request) throws Exception {
        log.debug("create transaction request: {}",request);
        return new ResponseEntity<TransactionResponse>(new TransactionResponse(service.createTransaction(request)), CREATED);
    }
}
