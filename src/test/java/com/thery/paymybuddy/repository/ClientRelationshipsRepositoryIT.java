package com.thery.paymybuddy.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRelationshipsRepositoryIT {

    @Autowired
    ClientRelationshipsRepository clientRelationshipsRepository;

    @Test
    void testExistsClientRelationshipsByClient_idAndFriendEmail_Success() {
    boolean exist = clientRelationshipsRepository.existsClientRelationshipsByClient_idAndFriendEmail(1L,"carol@example.com");
        Assertions.assertTrue(exist);
    }
    @Test
    void testExistsClientRelationshipsByClient_idAndFriendEmail_NotFound() {
        boolean exist = clientRelationshipsRepository.existsClientRelationshipsByClient_idAndFriendEmail(2L,"carol@example.com");
        Assertions.assertFalse(exist);
    }
}
