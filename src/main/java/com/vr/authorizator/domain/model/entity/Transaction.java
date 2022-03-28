package com.vr.authorizator.domain.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TRANSACTIONS")
@SQLDelete(sql="UPDATE TRANSACTIONS SET DELETED = TRUE, DELETED_AT = now() WHERE ID = ?")
public class Transaction {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CARD_ID")
    private Card card;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "CREATED_AT")
    private ZonedDateTime createdAt;

    public Transaction(Card card, final Double amount) {
        this.card = card;
        this.amount = amount;
        createdAt = ZonedDateTime.now();
    }
}
