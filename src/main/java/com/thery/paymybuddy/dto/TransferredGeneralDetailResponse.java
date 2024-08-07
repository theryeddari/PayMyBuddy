package com.thery.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * DTO (Data Transfer Object) representing successful details of a general transfer operation.
 */
@Getter
@AllArgsConstructor
public class TransferredGeneralDetailResponse {

    /**
     * List of individual transfer success details.
     */
    private List<TransferredGeneralDetailDTO> listTransferredSuccesses;

    /**
     * Empty constructor for Lombok.
     */
    public TransferredGeneralDetailResponse() {
        // empty constructor for lombok
    }
}
