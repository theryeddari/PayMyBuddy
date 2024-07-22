package com.thery.paymybuddy.services;

import static com.thery.paymybuddy.Exceptions.AuthenticationManagementServiceException.*;
import static com.thery.paymybuddy.Exceptions.ClientServiceException.*;
import static com.thery.paymybuddy.Exceptions.RelationShipsServiceException.*;
import static com.thery.paymybuddy.constants.MessagesServicesConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.thery.paymybuddy.Services.AuthenticationManagementService;
import com.thery.paymybuddy.Services.ClientService;
import com.thery.paymybuddy.Services.RelationShipsService;
import com.thery.paymybuddy.dto.AddRelationShipsRequest;
import com.thery.paymybuddy.dto.AddRelationShipsResponse;
import com.thery.paymybuddy.dto.RelationShipsDetailForTransferResponse;
import com.thery.paymybuddy.models.Client;
import com.thery.paymybuddy.models.ClientRelationships;
import com.thery.paymybuddy.repository.ClientRelationshipsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;


@ExtendWith(MockitoExtension.class)
public class RelationShipsServiceTest {

    @Mock
    private ClientRelationshipsRepository clientRelationshipsRepository;

    @Mock
    ClientService clientService;

    private AuthenticationManagementService authenticationManagementService;

    private RelationShipsService relationShipsService;

    @BeforeEach
    void setUp() {
        authenticationManagementService = mock(AuthenticationManagementService.class);
        relationShipsService = new RelationShipsService(clientRelationshipsRepository,clientService,authenticationManagementService);
    }

    @Test
    public void testAddRelationShips_Success() throws AddRelationShipsException, GetIdClientFromContextException, FindByEmailException, FindByIdException {
        AddRelationShipsRequest addRelationShipsRequest = new AddRelationShipsRequest();
        String clientId = "2";

        when(authenticationManagementService.getIdClientFromContext()).thenReturn(clientId);

        when(clientRelationshipsRepository.existsClientRelationshipsByClient_idAndFriendEmail(2L, addRelationShipsRequest.getEmail())).thenReturn(false);

        Client friend = mock(Client.class);
        Client client = mock(Client.class);
        ClientRelationships newClientRelationships = mock(ClientRelationships.class);

        when(clientService.findByEmail(addRelationShipsRequest.getEmail())).thenReturn(friend);
        when(clientService.findById(anyLong())).thenReturn(client);

        //stub newClientRelationships
        when(newClientRelationships.getFriend()).thenReturn(friend);


        ArgumentCaptor<ClientRelationships> captorNewClientRelationships = ArgumentCaptor.forClass(ClientRelationships.class);

        //stub save relationShips (one request not need to check before, use data)
        when(clientRelationshipsRepository.save(any(ClientRelationships.class))).thenReturn(newClientRelationships);

        AddRelationShipsResponse addRelationShipsResponse = relationShipsService.addRelationShips(addRelationShipsRequest);

        verify(clientService,times(1)).findByEmail(addRelationShipsRequest.getEmail());
        verify(clientRelationshipsRepository,times(1)).save(captorNewClientRelationships.capture());

        assertNotNull(captorNewClientRelationships.getValue().getFriend());
        assertNotNull(captorNewClientRelationships.getValue().getClient());

        assertEquals(ADD_RELATION_SUCCESS, addRelationShipsResponse.getMessageSuccess());

    }

    @Test
    public void testAddRelationShips_Exception() throws GetIdClientFromContextException, FindByEmailException {
        AddRelationShipsRequest addRelationShipsRequest = new AddRelationShipsRequest("noclient@exemple.com");

        when(authenticationManagementService.getIdClientFromContext()).thenReturn("2");
        FindByEmailException findByEmailException = new FindByEmailException(new RuntimeException());
        when(clientService.findByEmail(addRelationShipsRequest.getEmail())).thenThrow(findByEmailException);

        Exception exceptionExcepted = assertThrows(AddRelationShipsException.class, () -> relationShipsService.addRelationShips(addRelationShipsRequest));
        assertEquals(FindByEmailException.class, exceptionExcepted.getCause().getClass());
    }

    @Test
    public void testRelationShipsDetailForTransfer_Success() throws RelationShipsDetailForTransferException, GetIdClientFromContextException {
        String clientId = "2";

        when(authenticationManagementService.getIdClientFromContext()).thenReturn(clientId);

        ClientRelationships clientRelationships = mock(ClientRelationships.class);
        Client client = mock(Client.class);

        when(clientRelationships.getFriend()).thenReturn(client);
        when(client.getEmail()).thenReturn("alice@exemple.com");

        List<ClientRelationships> listFriendClientRelationShips = List.of(clientRelationships);
        when(clientRelationshipsRepository.findClientRelationshipsByClientId(anyLong())).thenReturn(listFriendClientRelationShips);

        // Appeler la méthode réelle du service
        RelationShipsDetailForTransferResponse relationShipsDetailForTransferResponse = relationShipsService.relationShipsDetailForTransfer();

        // Vérifier le résultat attendu
        assertEquals(client.getEmail(), relationShipsDetailForTransferResponse.getListFriendsRelationShipsEmail().getFirst());

    }

    @Test
    public void testRelationShipsDetailForTransfer_Exception() throws GetIdClientFromContextException {
        when(authenticationManagementService.getIdClientFromContext()).thenThrow(new GetIdClientFromContextException(new RuntimeException()));

        Exception exception = assertThrows(RelationShipsDetailForTransferException.class, () -> relationShipsService.relationShipsDetailForTransfer());
        assertEquals(GetIdClientFromContextException.class, exception.getCause().getClass());

    }
}
