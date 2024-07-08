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
     * Empty constructor for Lombok.
     */
    public TransferredGeneralDetailResponse(){
        // empty constructor for lombok
    }

    /**
     * List of individual transfer success details.
     */
    private List<TransferredGeneralDetailDTO> listTransferredSuccesses;
}
