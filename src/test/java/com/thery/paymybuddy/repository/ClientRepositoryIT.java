package com.thery.paymybuddy.repository;

import com.thery.paymybuddy.models.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryIT {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void TestConnexionWithFindByEmail() {
        // Test de la méthode findByEmail
        Client foundClient = clientRepository.findByEmail("alice@example.com");
        assertEquals("alice@example.com",foundClient.getEmail());
    }
    @Test
    void TestConnexionFindByEmailNotFound() {
        // Test de la méthode findByEmail
        Client foundClient = clientRepository.findByEmail("al@example.com");
        assertNull(foundClient);
    }
}
