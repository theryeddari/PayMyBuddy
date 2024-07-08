package com.thery.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) representing the saving of client.
 */
@AllArgsConstructor
@Getter
public class SavingClientResponse {

    /**
     * Default constructor for the SavingClientResponse class.
     */
    public SavingClientResponse() {
        // empty constructor for lombok
    }

    /**
     * The amount of savings for the client.
     */
    private double saving;
}
