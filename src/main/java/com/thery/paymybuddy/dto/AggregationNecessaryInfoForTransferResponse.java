package com.thery.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) for aggregating all necessary information for frontend page who do a transfer.
 */
@AllArgsConstructor
@Getter
public class AggregationNecessaryInfoForTransferResponse {
    /**
     * Default constructor.
     * This constructor is required by Lombok.
     */
    public AggregationNecessaryInfoForTransferResponse() {
        // empty constructor for lombok
    }
    TransferredGeneralDetailResponse transferredGeneralDetailResponse;
    RelationShipsDetailForTransferResponse relationShipsDetailForTransferResponse;
    SavingClientResponse savingClientResponse;

}
