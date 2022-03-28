package com.vr.authorizator.web.controller;

import com.vr.authorizator.AbstractComponentTest;
import com.vr.authorizator.domain.exception.CardNotExistsException;
import com.vr.authorizator.domain.exception.InsufficientBalanceException;
import com.vr.authorizator.domain.exception.InvalidPasswordException;
import com.vr.authorizator.domain.model.entity.Card;
import com.vr.authorizator.domain.service.TransactionService;
import com.vr.authorizator.util.Stub;
import com.vr.authorizator.web.controller.dto.CardCreateRequest;
import com.vr.authorizator.web.controller.dto.CardCreateResponse;
import com.vr.authorizator.web.controller.dto.TransactionRequest;
import com.vr.authorizator.web.controller.dto.TransactionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.Valid;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.CREATED;

public class TransactionControllerTest extends AbstractComponentTest {

    @Test
    public void shouldCreateNewTransaction() throws CardNotExistsException {
        // given
        String cardNumber = Stub.getRandomCardNumber();
        String password = Stub.getRandomPassword();
        Double amount = 20.0;
        TransactionRequest request = new TransactionRequest(cardNumber, password, amount);

        cardRepository.save(new Card(cardNumber, password));

        // when
        ResponseEntity<TransactionResponse> response = restTemplate.postForEntity(localUrl+localPort+"/transacoes", request, TransactionResponse.class);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody().getCardNumber(), cardNumber);
        assertEquals(response.getBody().getPassword(), password);

        Card card = cardRepository.findByCardNumber(cardNumber).orElseThrow(() -> new CardNotExistsException());

        assertEquals(card.getCardNumber(), cardNumber);
        assertEquals(card.getPassword(), password);
        assertEquals(card.getBalance(), 480);
        assertNotNull(card.getId());
        assertNotNull(card.getCreatedAt());
        assertNotNull(card.getUpdatedAt());
        assertEquals(card.getDeleted(), false);
        assertNull(card.getDeletedAt());
    }

    @Test
    public void shouldReturnCardNotExistsWhenCreateNewTransaction() throws CardNotExistsException {
        // given
        String cardNumber = Stub.getRandomCardNumber();
        String password = Stub.getRandomPassword();
        Double amount = 20.0;
        TransactionRequest request = new TransactionRequest(Stub.getRandomCardNumber(), password, amount);

        cardRepository.save(new Card(cardNumber, password));

        // when
        HttpClientErrorException ex = assertThrows(
                HttpClientErrorException.class,
                () -> restTemplate.postForEntity(localUrl+localPort+"/transacoes", request, TransactionResponse.class),
                ""
        );

        // then
        assertEquals(ex.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
        assertEquals(ex.getResponseBodyAsString(), new CardNotExistsException().getMessage());

        Card card = cardRepository.findByCardNumber(cardNumber).orElseThrow(() -> new CardNotExistsException());
        assertEquals(card.getCardNumber(), cardNumber);
        assertEquals(card.getPassword(), password);
        assertEquals(card.getBalance(), 500);
    }

    @Test
    public void shouldReturnInvalidPasswordWhenCreateNewTransaction() throws CardNotExistsException {
        // given
        String cardNumber = Stub.getRandomCardNumber();
        String password = Stub.getRandomPassword();
        Double amount = 20.0;
        TransactionRequest request = new TransactionRequest(cardNumber, Stub.getRandomPassword(), amount);

        cardRepository.save(new Card(cardNumber, password));

        // when
        HttpClientErrorException ex = assertThrows(
                HttpClientErrorException.class,
                () -> restTemplate.postForEntity(localUrl+localPort+"/transacoes", request, TransactionResponse.class),
                ""
        );

        // then
        assertEquals(ex.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
        assertEquals(ex.getResponseBodyAsString(), new InvalidPasswordException().getMessage());

        Card card = cardRepository.findByCardNumber(cardNumber).orElseThrow(() -> new CardNotExistsException());
        assertEquals(card.getCardNumber(), cardNumber);
        assertEquals(card.getPassword(), password);
        assertEquals(card.getBalance(), 500);
    }

    @Test
    public void shouldReturnInsufficientBalanceWhenCreateNewTransaction() throws CardNotExistsException {
        // given
        String cardNumber = Stub.getRandomCardNumber();
        String password = Stub.getRandomPassword();
        Double amount = 501.0;
        TransactionRequest request = new TransactionRequest(cardNumber, password, amount);

        cardRepository.save(new Card(cardNumber, password));

        // when
        HttpClientErrorException ex = assertThrows(
                HttpClientErrorException.class,
                () -> restTemplate.postForEntity(localUrl+localPort+"/transacoes", request, TransactionResponse.class),
                ""
        );

        // then
        assertEquals(ex.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
        assertEquals(ex.getResponseBodyAsString(), new InsufficientBalanceException().getMessage());

        Card card = cardRepository.findByCardNumber(cardNumber).orElseThrow(() -> new CardNotExistsException());
        assertEquals(card.getCardNumber(), cardNumber);
        assertEquals(card.getPassword(), password);
        assertEquals(card.getBalance(), 500);
    }

}
