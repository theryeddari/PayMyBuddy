package com.thery.paymybuddy.repository;

import com.thery.paymybuddy.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing Client entities.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    /**
     * Retrieve a Client entity by its email address.
     *
     * @param email The email address of the client to retrieve.
     * @return The Client entity corresponding to the provided email address, or null if not found.
     */
    Client findByEmail(String email);
    /**
     * Retrieve a boolean entity by its email address.
     *
     * @param email The email address of the client to retrieve.
     * @return The boolean entity corresponding to the provided email address, true if found otherwise false.
     */
    boolean existsByEmail(String email);

}