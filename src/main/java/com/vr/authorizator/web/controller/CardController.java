package com.vr.authorizator.web.controller;

import com.vr.authorizator.domain.exception.CardAlredyExistsException;
import com.vr.authorizator.domain.exception.CardNotExistsException;
import com.vr.authorizator.domain.service.CardService;
import com.vr.authorizator.web.controller.dto.CardCreateRequest;
import com.vr.authorizator.web.controller.dto.CardCreateResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/cartoes")
public class CardController {

    @Autowired
    private CardService service;

    @PostMapping
    public ResponseEntity<CardCreateResponse> createCard(@RequestBody @Valid final CardCreateRequest request) throws CardAlredyExistsException {
        log.debug("createcard request:{}", request);
        return new ResponseEntity<>(new CardCreateResponse(service.createCard(request)), CREATED);
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<Double> getBalance(@PathVariable("numeroCartao") final String cardNumber) throws CardNotExistsException {
        log.debug("getbalance cardNumber:{}", cardNumber);
        return ResponseEntity.ok(service.getBalance(cardNumber).getBalance());
    }
}
