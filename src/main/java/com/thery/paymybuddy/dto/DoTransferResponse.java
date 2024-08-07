package com.thery.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) representing the success response after a transfer.
 */
@Getter
@AllArgsConstructor
public class DoTransferResponse {

    /**
     * Success message indicating the transfer was successful.
     */
    private String messageSuccess;

    /**
     * Default constructor required by Lombok for instantiation.
     */
    public DoTransferResponse() {
        // empty constructor for lombok
    }
}
