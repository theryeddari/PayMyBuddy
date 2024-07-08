package com.thery.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO (Data Transfer Object) representing the response for a successful profile client change.
 */
@AllArgsConstructor
@Getter
public class ProfileClientChangeResponse {

    /**
     * Default constructor required by Lombok for its annotations.
     */
    public ProfileClientChangeResponse(){
        // empty constructor for lombok
    }

    /**
     * A success message indicating the result of the profile change operation.
     */
    private String messageSuccess;
}