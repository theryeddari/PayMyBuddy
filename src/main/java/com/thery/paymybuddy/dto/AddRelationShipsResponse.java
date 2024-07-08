package com.thery.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) representing a successful addition of relationships.
 */
@AllArgsConstructor
@Getter
public class AddRelationShipsResponse {

    /**
     * Empty constructor required by Lombok.
     */
    public AddRelationShipsResponse(){
        // empty constructor for lombok
    }

    /**
     * A success message indicating the result of add relationships operation.
     */
    private String messageSuccess;
}
