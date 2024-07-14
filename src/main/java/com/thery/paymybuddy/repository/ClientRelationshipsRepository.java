package com.thery.paymybuddy.repository;

import com.thery.paymybuddy.models.ClientRelationships;
import com.thery.paymybuddy.models.ClientRelationshipsPrimaryKeyComposite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing ClientRelationships entities.
 */
@Repository
public interface ClientRelationshipsRepository extends JpaRepository<ClientRelationships, ClientRelationshipsPrimaryKeyComposite> {

   /**
    * Checks if a ClientRelationships entry exists for a given client ID and friend's email.
    *
    * @param clientId   The ID of the client.
    * @param friendMail The email of the friend.
    * @return {@code true} if a relationship exists, {@code false} otherwise.
    */
   boolean existsClientRelationshipsByClient_idAndFriendEmail(long clientId, String friendMail);
}