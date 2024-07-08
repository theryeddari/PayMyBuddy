package com.thery.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) representing the modification profile of a client.
 */
@AllArgsConstructor
@Getter
public class ProfileClientChangeRequest {

    /**
     * Default constructor for the ProfileChangeClientDTO class.
     */
    public ProfileClientChangeRequest() {
        // empty constructor for lombok
    }

    /**
     * The username of the client.
     */
    private String username;

    /**
     * The email address of the client.
     */
    private String email;

    /**
     * The password of the client.
     */
    private String password;
}
