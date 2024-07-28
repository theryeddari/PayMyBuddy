package com.thery.paymybuddy.repository;

import com.thery.paymybuddy.model.ClientRelationships;
import com.thery.paymybuddy.model.ClientRelationshipsPrimaryKeyComposite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    * @return ClientRelationShips save
    */
   boolean existsClientRelationshipsByClient_idAndFriendEmail(long clientId, String friendMail);

   /**
    * retrieve ClientRelationships list of a client.
    *
    * @param clientId   The ID of the client.
    * @return list of ClientRelationShips
    */
   List<ClientRelationships> findClientRelationshipsByClientId(long clientId);
}