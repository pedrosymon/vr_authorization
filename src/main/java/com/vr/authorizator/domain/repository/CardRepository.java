package com.vr.authorizator.domain.repository;

import com.vr.authorizator.domain.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByCardNumber(String numeroCartao);

    @Modifying
    @Query("update Card c set c.balance = (c.balance - :amount) WHERE c.id = :cardId")
    void updateBalance(@Param("cardId") Long id, @Param("amount") Double amount);
}
