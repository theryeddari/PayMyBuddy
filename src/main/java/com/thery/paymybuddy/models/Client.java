package com.thery.paymybuddy.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a client/user entity in the application.
 */
@Getter
@Setter
@Entity
@Table(name = "client")
public class Client {

    /**
     * The unique identifier for the client.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username of the client.
     */
    @Column(name = "username", nullable = false, length = 50)
    private String username;

    /**
     * The email address of the client.
     */
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    /**
     * The password of the client.
     */
    @Column(name = "password", nullable = false, length = 100)
    private String password;

    /**
     * The role of the client.
     */
    @Column(name = "role", nullable = false, length = 20)
    private String role;

    /**
     * The saving of the client.
     */
    @Column(name = "saving", nullable = false)
    private double saving;
}