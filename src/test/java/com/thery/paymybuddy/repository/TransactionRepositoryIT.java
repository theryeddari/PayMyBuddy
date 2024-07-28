package com.thery.paymybuddy.repository;

import com.thery.paymybuddy.model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionRepositoryIT {
    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void testConnexionByFindBySenderId(){
        List<Transaction> list = transactionRepository.findBySender_Id(2L);
        Long receiverId = list.getFirst().getReceiver().getId();
        String description = list.getFirst().getDescription();
        Assertions.assertEquals(1,receiverId);
        Assertions.assertEquals("Payment for service",description);
    }
    @Test
    void testConnexionByFindBySenderIdNorFound(){
        List<Transaction> list = transactionRepository.findBySender_Id(5L);
        Assertions.assertTrue(list.isEmpty());
    }
}
