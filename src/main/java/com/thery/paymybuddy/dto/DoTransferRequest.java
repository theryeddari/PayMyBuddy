package com.thery.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) representing details for a money transfer.
 */
@Getter
@AllArgsConstructor
public class DoTransferRequest {

    /**
     * Default constructor required by Lombok for internal use.
     */
    public DoTransferRequest() {
        // empty constructor for lombok
    }

    /**
     * Email address of the receiver.
     */
    private String receiverEmail;

    /**
     * Description or note accompanying the transfer.
     */
    private String description;

    /**
     * Amount of money to be transferred.
     */
    private Double amount;
}
