package com.thery.paymybuddy.controller;

import com.thery.paymybuddy.dto.AddRelationShipsRequest;
import com.thery.paymybuddy.dto.AddRelationShipsResponse;
import com.thery.paymybuddy.dto.RelationShipsDetailForTransferResponse;
import com.thery.paymybuddy.service.RelationShipsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RelationShipsControllerTest {

    @InjectMocks
    RelationShipsController relationShipsController;

    @Mock
    private RelationShipsService relationShipsService;

    @Test
    void addRelationShips_success() throws Exception {
        AddRelationShipsRequest addRelationShipsRequest = new AddRelationShipsRequest("friend@gmail.com");
        AddRelationShipsResponse addRelationShipsSuccessDTO = new AddRelationShipsResponse("The addition of your relationship was completed correctly");

        when(relationShipsService.addRelationShips(addRelationShipsRequest)).thenReturn(addRelationShipsSuccessDTO);

        AddRelationShipsResponse result = relationShipsController.addRelationShips(addRelationShipsRequest);

        assertEquals(addRelationShipsSuccessDTO, result);
        verify(relationShipsService).addRelationShips(addRelationShipsRequest);
    }

    @Test
    void relationShipsDetailForTransfer_success() throws Exception {
        RelationShipsDetailForTransferResponse detailForTransferDTO = new RelationShipsDetailForTransferResponse(List.of("FriendA", "FriendB"));

        when(relationShipsService.relationShipsDetailForTransfer()).thenReturn(detailForTransferDTO);

        RelationShipsDetailForTransferResponse result = relationShipsController.relationShipsDetailForTransfer();

        assertEquals(detailForTransferDTO, result);
        verify(relationShipsService).relationShipsDetailForTransfer();
    }

}
