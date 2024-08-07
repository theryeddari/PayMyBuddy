package com.thery.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) for adding relationships.
 */
@AllArgsConstructor
@Getter
public class AddRelationShipsRequest {

    /**
     * Email of the user to establish a relationship.
     */
    private String email;

    /**
     * Default constructor.
     * This constructor is required by Lombok.
     */
    public AddRelationShipsRequest() {
        // empty constructor for lombok
    }
}

