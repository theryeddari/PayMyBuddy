package com.thery.paymybuddy.model;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Represents the composite primary key for the relationship between a user and their friend.
 * This class is used as the primary key in the mapping of client friends.
 */
@Embeddable
@EqualsAndHashCode
public class ClientRelationshipsPrimaryKeyComposite implements Serializable {
    private Client client;    // The Entity Client with ID of Client user in properties
    private Client friend;  // The Entity Client with ID of Client friend in properties


    // Getters and setters can be added if necessary
}
