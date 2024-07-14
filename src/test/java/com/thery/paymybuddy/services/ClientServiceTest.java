package com.thery.paymybuddy.services;

import static com.thery.paymybuddy.Exceptions.ClientServiceException.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.thery.paymybuddy.Services.ClientService;
import com.thery.paymybuddy.dto.*;
import com.thery.paymybuddy.dto.ProfileClientChangeRequest;
import com.thery.paymybuddy.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    public void testGetProfile_Success() throws GetProfileException {
        ProfileClientResponse actualDTO = clientService.getProfile();
    }

    @Test
    public void testGetProfile_Exception() throws GetProfileException {
        assertThrows(GetProfileException.class, () -> clientService.getProfile());
    }

    @Test
    public void testChangeProfile_Success() throws ChangeProfileException {
        ProfileClientChangeRequest profileClientChangeRequest = new ProfileClientChangeRequest();
        ProfileClientChangeResponse actualDTO = clientService.changeProfile(profileClientChangeRequest);
    }

    @Test
    public void testChangeProfile_Exception() throws ChangeProfileException {
        ProfileClientChangeRequest profileClientChangeRequest = new ProfileClientChangeRequest();
        assertThrows(ChangeProfileException.class, () -> clientService.changeProfile(profileClientChangeRequest));
    }

    @Test
    public void testGetSavingClient_Success() throws GetSavingClientException {
        SavingClientResponse actualDTO = clientService.getSavingClient();
    }

    @Test
    public void testGetSavingClient_Exception() throws GetSavingClientException {
        assertThrows(GetSavingClientException.class, () -> clientService.getSavingClient());
    }
}
