package com.thery.paymybuddy.repository;

import com.thery.paymybuddy.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing transactions in the database.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Retrieves a list of transactions sent by a specific customer.
     * @param customerId The ID of the customer whose transactions are to be retrieved.
     * @return A list of transactions sent by the specified customer.
     */
    List<Transaction> findBySender_Id(Long customerId);

}
