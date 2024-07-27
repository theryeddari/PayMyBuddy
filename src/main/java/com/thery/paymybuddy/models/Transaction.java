package com.thery.paymybuddy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a financial transaction between two clients.
 */
@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Description of the transaction.
     */
    @Column(name = "description", nullable = false, length = 100)
    private String description;

    /**
     * Amount of money involved in the transaction.
     */
    @Column(name = "amount", nullable = false)
    private Double amount;

    /**
     * The client who initiated (sent) the transaction.
     */
    @ManyToOne
    @JoinColumn(name = "sender", nullable = false)
    private Client sender;

    /**
     * The client who received the transaction.
     */
    @ManyToOne
    @JoinColumn(name = "receiver", nullable = false)
    private Client receiver;

    // Getters and setters are provided by Lombok annotations
}