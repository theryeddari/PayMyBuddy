package com.thery.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) for user sign-in information.
 */
@Getter
@AllArgsConstructor
public class SignInRequest {

    /**
     * Default constructor for SignInRequest.
     */
    public SignInRequest() {
        // empty constructor for lombok
    }

    /**
     * The email address of the user.
     */
    private String email;

    /**
     * The password of the user.
     */
    private String password;
}
