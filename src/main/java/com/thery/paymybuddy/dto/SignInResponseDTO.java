package com.thery.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignInResponseDTO {
    /**
     * Default constructor for Lombok.
     */
    public SignInResponseDTO() {
        // empty constructor for lombok
    }

    /**
     * JWT token issued upon successful sign-in.
     */
    private String jwtToken;
    /**
     * Dto upon successful sign-in.
     */
    private SignInResponse signInResponse;

}
