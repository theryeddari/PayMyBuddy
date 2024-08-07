package com.thery.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO class for representing successful transfer information.
 */
@Getter
@AllArgsConstructor
public class TransferredGeneralDetailDTO {

    /**
     * The email address of the receiver of the transfer.
     */
    private String receiverEmail;
    /**
     * Description or note associated with the transfer.
     */
    private String description;
    /**
     * The amount of money transferred.
     */
    private Double amount;

    /**
     * Default constructor for the TransferredGeneralDetailDTO class.
     * Required by Lombok for instantiation.
     */
    public TransferredGeneralDetailDTO() {
        // empty constructor for lombok
    }
}
