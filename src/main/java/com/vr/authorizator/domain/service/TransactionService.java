package com.vr.authorizator.domain.service;

import com.vr.authorizator.domain.exception.CardNotExistsException;
import com.vr.authorizator.domain.exception.InsufficientBalanceException;
import com.vr.authorizator.domain.exception.InvalidPasswordException;
import com.vr.authorizator.domain.repository.TransactionRepository;
import com.vr.authorizator.domain.model.entity.Card;
import com.vr.authorizator.domain.model.entity.Transaction;
import com.vr.authorizator.web.controller.dto.TransactionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private CardService cardService;

    @Autowired
    private LockService locker;

    @Transactional
    public Transaction createTransaction(final TransactionRequest request) throws Exception {
        return (Transaction) locker.lock(request.getCardNumber(), () ->
        {
            Card card = cardService.findCard(request.getCardNumber()).orElseThrow(() -> new CardNotExistsException());

            validatePassword(card.getPassword(), request.getPassword());
            validateBalance(card.getBalance(), request.getAmount());

            cardService.updateBalance(card.getId(), request.getAmount());
            return repository.save(new Transaction(card, request.getAmount()));
        });
    }

    private void validatePassword(final String cardPassword, final String transactionPassword) throws InvalidPasswordException {
        if(!cardPassword.equals(transactionPassword)) throw new InvalidPasswordException();
    }

    private void validateBalance(final Double cardBalance, final Double transactionAmount) throws InsufficientBalanceException {
        if(cardBalance < transactionAmount) throw new InsufficientBalanceException();
    }
}
