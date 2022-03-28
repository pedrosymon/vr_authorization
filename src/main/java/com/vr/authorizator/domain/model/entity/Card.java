package com.vr.authorizator.domain.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "CARDS")
@SQLDelete(sql="UPDATE CARDS SET DELETED = TRUE, DELETED_AT = now() WHERE ID = ?")
public class Card {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CARD_NUMBER", unique = true)
    private String cardNumber;

    @Column(name = "PASSWORD")
    private String password;

    @Min(0)
    @Column(name = "BALANCE")
    private double balance;

    @Column(name = "CREATED_AT")
    private ZonedDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private ZonedDateTime updatedAt;

    @Column(name = "DELETED_AT")
    private ZonedDateTime deletedAt;

    @Column(name = "DELETED")
    private Boolean deleted;

    @Transient
    private Double DEFAULT_BALANCE = 500.0;

    public Card(final String cardNumber, final String password){
        this.cardNumber = cardNumber;
        this.password = password;
        this.balance = DEFAULT_BALANCE;
        createdAt = ZonedDateTime.now();
        deleted = false;
        updatedAt = ZonedDateTime.now();
    }
}
