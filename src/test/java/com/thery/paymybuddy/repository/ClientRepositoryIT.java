package com.thery.paymybuddy.repository;

import com.thery.paymybuddy.model.Client;
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
    @Test
    void TestExistsByEmail() {
        // Test de la méthode existsByEmail
        boolean foundClient = clientRepository.existsByEmail("alice@example.com");
        assertTrue(foundClient);
    }
    @Test
    void TestExistsByEmail_NotFound() {
        // Test de la méthode existsByEmail
        boolean foundClient = clientRepository.existsByEmail("zf@example.com");
        assertFalse(foundClient);
    }
    @Test
    void TestSaveClient_Success() {
        // Test de la méthode save
        Client client = new Client();
        client.setEmail("renault@example.com");
        client.setSaving(100);
        client.setUsername("renault");
        client.setPassword("renaultp");
        client.setRole("CLIENT");
        Client foundClient = clientRepository.save(client);
        assertEquals(client.getEmail(),foundClient.getEmail());
    }

}
