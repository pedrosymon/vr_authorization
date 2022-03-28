package com.vr.authorizator.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vr.authorizator.AbstractComponentTest;
import com.vr.authorizator.domain.exception.CardNotExistsException;
import com.vr.authorizator.domain.model.entity.Card;
import com.vr.authorizator.util.Stub;
import com.vr.authorizator.web.controller.dto.CardCreateRequest;
import com.vr.authorizator.web.controller.dto.CardCreateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardControllerTest extends AbstractComponentTest {

    @Test
    public void shouldCreateNewCard() throws CardNotExistsException {
        // given
        String cardNumber = Stub.getRandomCardNumber();
        String password = Stub.getRandomPassword();
        CardCreateRequest request = new CardCreateRequest(cardNumber, password);

        // when
        ResponseEntity<CardCreateResponse> response = restTemplate.postForEntity(localUrl+localPort+"/cartoes", request, CardCreateResponse.class);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        assertEquals(response.getBody().getCardNumber(), cardNumber);
        assertEquals(response.getBody().getPassword(), password);

        Card card = cardRepository.findByCardNumber(cardNumber).orElseThrow(() -> new CardNotExistsException());

        assertEquals(card.getCardNumber(), cardNumber);
        assertEquals(card.getPassword(), password);
        assertEquals(card.getBalance(), 500);
        assertNotNull(card.getId());
        assertNotNull(card.getCreatedAt());
        assertNotNull(card.getUpdatedAt());
        assertEquals(card.getDeleted(), false);
        assertNull(card.getDeletedAt());
    }

    @Test
    public void shouldNotCreateNewCardWhenItAlreadyExists() throws JsonProcessingException {

        // given
        String cardNumber = Stub.getRandomCardNumber();
        String password = Stub.getRandomPassword();
        CardCreateRequest request = new CardCreateRequest(cardNumber, password);

        cardRepository.save(new Card(cardNumber, password));

        // when
        HttpClientErrorException ex = assertThrows(
                HttpClientErrorException.class,
                () -> restTemplate.postForEntity(localUrl+localPort+"/cartoes", request, CardCreateResponse.class),
                ""
        );

        // then
        assertEquals(ex.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
        assertEquals(ex.getResponseBodyAsString(), objectMapper.writeValueAsString(request));

        List<Card> all = cardRepository.findAll();
        assertEquals(all.size(), 1);
    }

    @Test
    public void shouldReturnBalance() {
        // given
        String cardNumber = Stub.getRandomCardNumber();
        String password = Stub.getRandomPassword();
        CardCreateRequest request = new CardCreateRequest(cardNumber, password);

        cardRepository.save(new Card(cardNumber, password));

        // when
        ResponseEntity<Double> response = restTemplate.getForEntity(localUrl+localPort+"/cartoes/"+cardNumber, Double.class);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), 500);
    }

    @Test
    public void shouldNotReturnBalanceWhenCardNotExists(){
        // given
        String cardNumber = Stub.getRandomCardNumber();
        String password = Stub.getRandomPassword();
        CardCreateRequest request = new CardCreateRequest(cardNumber, password);

        cardRepository.save(new Card(cardNumber, password));

        // when
        ResponseEntity<Double> response = restTemplate.getForEntity(localUrl+localPort+"/cartoes/"+cardNumber, Double.class);

        // then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), 500);
    }
}
