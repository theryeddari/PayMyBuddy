package com.thery.paymybuddy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * DTO (Data Transfer Object) for encapsulating general relationship details needed for transfers.
 */
@Getter
@AllArgsConstructor
public class RelationShipsDetailForTransferResponse {

    /**
     * Default constructor needed for Lombok.
     */
    public RelationShipsDetailForTransferResponse() {
        // empty constructor for Lombok
    }

    /**
     * List of email addresses for known friend relationships.
     */
    private List<String> listFriendsRelationShipsEmail;
}
