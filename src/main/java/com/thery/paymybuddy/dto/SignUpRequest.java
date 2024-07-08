package com.thery.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO (Data Transfer Object) class for user sign-up information.
 */
@Getter
@AllArgsConstructor
public class SignUpRequest {

    /**
     * Default constructor needed for Lombok.
     */
    public SignUpRequest() {
        // empty constructor for lombok
    }

    /**
     * The username of the user signing up.
     */
    private String username;

    /**
     * The email address of the user signing up.
     */
    private String email;

    /**
     * The password chosen by the user for signing up.
     */
    private String password;
}
