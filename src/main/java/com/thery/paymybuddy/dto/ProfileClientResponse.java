package com.thery.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) representing client profile information.
 */
@AllArgsConstructor
@Getter
public class ProfileClientResponse {

    /**
     * Default constructor for ProfileClientDTO.
     * Required by Lombok for internal usage.
     */
    public ProfileClientResponse(){
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
}