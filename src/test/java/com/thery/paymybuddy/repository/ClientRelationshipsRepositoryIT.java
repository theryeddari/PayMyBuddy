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
    void testConnexionWithExistsClientFriendsByMailAndId() {
    boolean exist = clientRelationshipsRepository.existsClientRelationshipsByClient_idAndFriendEmail(1L,"bob@example.com");
        Assertions.assertTrue(exist);
    }
    @Test
    void testConnexionWithNotExistsClientFriendsByMailAndId() {
        boolean exist = clientRelationshipsRepository.existsClientRelationshipsByClient_idAndFriendEmail(2L,"alice@example.com");
        Assertions.assertFalse(exist);
    }

}
