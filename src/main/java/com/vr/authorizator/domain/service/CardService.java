package com.vr.authorizator.domain.service;

import com.vr.authorizator.domain.exception.CardAlredyExistsException;
import com.vr.authorizator.domain.exception.CardNotExistsException;
import com.vr.authorizator.domain.repository.CardRepository;
import com.vr.authorizator.domain.model.entity.Card;
import com.vr.authorizator.web.controller.dto.CardCreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CardService {

    @Autowired
    private CardRepository repository;

    public Card createCard(final CardCreateRequest request) throws CardAlredyExistsException {
        try {
            return repository.save(new Card(request.getCardNumber(), request.getPassword()));
        }catch (DataIntegrityViolationException e){
            throw new CardAlredyExistsException(request);
        }
    }

    public Card getBalance(final String cardNumber) throws CardNotExistsException {
        return findCard(cardNumber).orElseThrow(() -> new CardNotExistsException());
    }

    public Optional<Card> findCard(String cardNumber) {
        return repository.findByCardNumber(cardNumber);
    }

    public void updateBalance(final Long cardId, final Double amount) {
        repository.updateBalance(cardId, amount);
    }
}
