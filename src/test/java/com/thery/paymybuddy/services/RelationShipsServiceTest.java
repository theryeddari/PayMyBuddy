package com.thery.paymybuddy.services;

import static com.thery.paymybuddy.Exceptions.RelationShipsServiceException.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import com.thery.paymybuddy.Services.RelationShipsService;
import com.thery.paymybuddy.dto.AddRelationShipsRequest;
import com.thery.paymybuddy.dto.AddRelationShipsResponse;
import com.thery.paymybuddy.dto.RelationShipsDetailForTransferResponse;
import com.thery.paymybuddy.repository.ClientRelationshipsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class RelationShipsServiceTest {

    @Mock
    private ClientRelationshipsRepository clientRelationshipsRepository;

    @InjectMocks
    private RelationShipsService relationShipsService;

    @Test
    public void testAddRelationShips_Success() throws AddRelationShipsException {
        AddRelationShipsRequest addRelationShipsDTO = new AddRelationShipsRequest();
        AddRelationShipsResponse actualDTO = relationShipsService.addRelationShips(addRelationShipsDTO);
    }

    @Test
    public void testAddRelationShips_Exception() throws AddRelationShipsException {
        assertThrows(AddRelationShipsException.class, () -> relationShipsService.addRelationShips(any(AddRelationShipsRequest.class)));
    }

    @Test
    public void testRelationShipsDetailForTransfer_Success() throws RelationShipsDetailForTransferException {
        RelationShipsDetailForTransferResponse actualDTO = relationShipsService.relationShipsDetailForTransfer();
    }

    @Test
    public void testRelationShipsDetailForTransfer_Exception() throws RelationShipsDetailForTransferException {
        assertThrows(RelationShipsDetailForTransferException.class, () -> relationShipsService.relationShipsDetailForTransfer());
    }
}
