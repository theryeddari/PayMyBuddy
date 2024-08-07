package com.thery.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) representing the response for a successful sign-in.
 */
@Getter
@AllArgsConstructor
public class SignInResponse {

    /**
     * A welcoming message or any additional information to be displayed after sign-in.
     */
    private String welcomingMessage;

    /**
     * Default constructor for Lombok.
     */
    public SignInResponse() {
        // empty constructor for lombok
    }
}
