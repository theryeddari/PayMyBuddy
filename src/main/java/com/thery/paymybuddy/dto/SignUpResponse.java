package com.thery.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO (Data Transfer Object) representing the confirmation details for a signed-up user.
 */
@Getter
@AllArgsConstructor
public class SignUpResponse {

    /**
     * A success message indicating the result of the saving operation.
     */
    private String messageSuccess;

    /**
     * Default constructor for the SignedUpConfirmDTO class.
     */
    public SignUpResponse() {
        // empty constructor for lombok
    }
}
