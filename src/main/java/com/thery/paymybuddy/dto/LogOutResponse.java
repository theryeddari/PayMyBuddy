package com.thery.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) for representing successful logout response.
 */
@Getter
@AllArgsConstructor
public class LogOutResponse {

    /**
     * Default constructor for Lombok.
     */
    public LogOutResponse() {
        // empty constructor for lombok
    }

    /**
     * Farewell message displayed after successful logout.
     */
    private String goodByeMessage;

}