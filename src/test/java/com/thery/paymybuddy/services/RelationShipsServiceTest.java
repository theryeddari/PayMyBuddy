package com.thery.paymybuddy.services;

import static com.thery.paymybuddy.Exceptions.ClientServiceException.*;
import static com.thery.paymybuddy.Exceptions.InformationOnContextUtilsException.*;
import static com.thery.paymybuddy.Exceptions.RelationShipsServiceException.*;
import static com.thery.paymybuddy.Exceptions.RelationShipsServiceException.RelationshipsAlreadyExistException.*;
import static com.thery.paymybuddy.constants.MessagesServicesConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.thery.paymybuddy.Services.ClientService;
import com.thery.paymybuddy.Services.RelationShipsService;
import com.thery.paymybuddy.dto.AddRelationShipsRequest;
import com.thery.paymybuddy.dto.AddRelationShipsResponse;
import com.thery.paymybuddy.dto.RelationShipsDetailForTransferResponse;
import com.thery.paymybuddy.models.Client;
import com.thery.paymybuddy.models.ClientRelationships;
import com.thery.paymybuddy.repository.ClientRelationshipsRepository;
import com.thery.paymybuddy.utils.InformationOnContextUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;


@ExtendWith(MockitoExtension.class)
public class RelationShipsServiceTest {

    @Mock
    private ClientRelationshipsRepository clientRelationshipsRepository;

    @Mock
    ClientService clientService;

    @InjectMocks
    private RelationShipsService relationShipsService;


    @Test
    public void testAddRelationShips_Success() throws AddRelationShipsException, FindByEmailException, FindByIdException {
        AddRelationShipsRequest addRelationShipsRequest = new AddRelationShipsRequest("alice@exemple.com");
        String clientId = "2";

        try (MockedStatic<InformationOnContextUtils> informationOnContextUtilsMockedStatic = mockStatic(InformationOnContextUtils.class)) {
            // Mock the static method
            informationOnContextUtilsMockedStatic.when(InformationOnContextUtils::getIdClientFromContext).thenReturn(clientId);

            when(clientRelationshipsRepository.existsClientRelationshipsByClient_idAndFriendEmail(2L, addRelationShipsRequest.getEmail())).thenReturn(false);

            Client friend = mock(Client.class);
            Client client = mock(Client.class);
            ClientRelationships newClientRelationships = mock(ClientRelationships.class);

            when(clientService.findByEmail(addRelationShipsRequest.getEmail())).thenReturn(friend);
            when(clientService.findById(anyLong())).thenReturn(client);


            ArgumentCaptor<ClientRelationships> captorNewClientRelationships = ArgumentCaptor.forClass(ClientRelationships.class);

            //stub save relationShips (one request not need to check before, use data)
            when(clientRelationshipsRepository.save(any(ClientRelationships.class))).thenReturn(newClientRelationships);

            AddRelationShipsResponse addRelationShipsResponse = relationShipsService.addRelationShips(addRelationShipsRequest);

            verify(clientService, times(1)).findByEmail(addRelationShipsRequest.getEmail());
            verify(clientRelationshipsRepository, times(1)).save(captorNewClientRelationships.capture());

            assertNotNull(captorNewClientRelationships.getValue().getFriend());
            assertNotNull(captorNewClientRelationships.getValue().getClient());

            assertEquals(ADD_RELATION_SUCCESS, addRelationShipsResponse.getMessageSuccess());
     }
}

    @Test
    public void testAddRelationShips_RelationshipsAlreadyExistException() throws FindByEmailException {
        AddRelationShipsRequest addRelationShipsRequest = new AddRelationShipsRequest("noclient@exemple.com");
        String clientId = "2";

        try (MockedStatic<InformationOnContextUtils> informationOnContextUtilsMockedStatic = mockStatic(InformationOnContextUtils.class)) {
            // Mock the static method
            informationOnContextUtilsMockedStatic.when(InformationOnContextUtils::getIdClientFromContext).thenReturn(clientId);

//            FindByEmailException findByEmailException = new FindByEmailException(new RuntimeException());
//            when(clientService.findByEmail(addRelationShipsRequest.getEmail())).thenThrow(findByEmailException);
            when(clientRelationshipsRepository.existsClientRelationshipsByClient_idAndFriendEmail(anyLong(), anyString())).thenReturn(true);

            Exception exception = assertThrows(AddRelationShipsException.class, () -> relationShipsService.addRelationShips(addRelationShipsRequest));
//            assertEquals(FindByEmailException.class, exception.getCause().getClass());
            assertEquals(RelationshipsAlreadyExistException.class, exception.getCause().getClass());
        }
    }

    @Test
    public void testAddRelationShips_SelfOrientedRelationshipException() throws FindByEmailException {
        AddRelationShipsRequest addRelationShipsRequest = new AddRelationShipsRequest("noclient@exemple.com");
        String clientId = "2";

        try (MockedStatic<InformationOnContextUtils> informationOnContextUtilsMockedStatic = mockStatic(InformationOnContextUtils.class)) {
            // Mock the static method
            informationOnContextUtilsMockedStatic.when(InformationOnContextUtils::getIdClientFromContext).thenReturn(clientId);

            when(clientRelationshipsRepository.existsClientRelationshipsByClient_idAndFriendEmail(2L, addRelationShipsRequest.getEmail())).thenReturn(false);

            Client friend = mock(Client.class);
            when(friend.getId()).thenReturn(Long.parseLong(clientId));
            when(clientService.findByEmail(addRelationShipsRequest.getEmail())).thenReturn(friend);

            Exception exception = assertThrows(AddRelationShipsException.class, () -> relationShipsService.addRelationShips(addRelationShipsRequest));
            assertEquals(SelfOrientedRelationshipException.class, exception.getCause().getClass());
        }
    }

    @Test
    public void testRelationShipsDetailForTransfer_Success() throws RelationShipsDetailForTransferException {
        String clientId = "2";

        try (MockedStatic<InformationOnContextUtils> informationOnContextUtilsMockedStatic = mockStatic(InformationOnContextUtils.class)) {
            // Mock the static method
            informationOnContextUtilsMockedStatic.when(InformationOnContextUtils::getIdClientFromContext).thenReturn(clientId);

            ClientRelationships clientRelationships = mock(ClientRelationships.class);
            Client client = mock(Client.class);

            when(clientRelationships.getFriend()).thenReturn(client);
            when(client.getEmail()).thenReturn("alice@exemple.com");

            List<ClientRelationships> listFriendClientRelationShips = List.of(clientRelationships);
            when(clientRelationshipsRepository.findClientRelationshipsByClientId(anyLong())).thenReturn(listFriendClientRelationShips);

            RelationShipsDetailForTransferResponse relationShipsDetailForTransferResponse = relationShipsService.relationShipsDetailForTransfer();

            assertEquals(client.getEmail(), relationShipsDetailForTransferResponse.getListFriendsRelationShipsEmail().getFirst());

    }
}
    @Test
    public void testRelationShipsDetailForTransfer_Exception() {
        try (MockedStatic<InformationOnContextUtils> informationOnContextUtilsMockedStatic = mockStatic(InformationOnContextUtils.class)) {
            // Mock the static method
            informationOnContextUtilsMockedStatic.when(InformationOnContextUtils::getIdClientFromContext).thenThrow(new GetIdClientFromContextException(new RuntimeException()));
        }

        Exception exception = assertThrows(RelationShipsDetailForTransferException.class, () -> relationShipsService.relationShipsDetailForTransfer());
        assertEquals(GetIdClientFromContextException.class, exception.getCause().getClass());

    }
}
