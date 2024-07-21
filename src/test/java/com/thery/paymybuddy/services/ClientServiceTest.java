package com.thery.paymybuddy.services;

import static com.thery.paymybuddy.Exceptions.ClientServiceException.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.thery.paymybuddy.Services.ClientService;
import com.thery.paymybuddy.dto.*;
import com.thery.paymybuddy.dto.ProfileClientChangeRequest;
import com.thery.paymybuddy.models.Client;
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
    @Test
    public void testFindByEmail_Success() throws FindByEmailException {
        when(clientRepository.findByEmail(anyString())).thenReturn(new Client());
        Client client = clientService.findByEmail(anyString());
        assertNotNull(client);
    }

    @Test
    public void testFindByEmail_ClientNotFoundException() {
        when(clientRepository.findByEmail(anyString())).thenReturn(null);
        Exception exceptionExcepted = assertThrows(FindByEmailException.class, () -> clientService.findByEmail(anyString()));
        assertEquals(ClientNotFoundException.class, exceptionExcepted.getCause().getClass());
    }

    @Test
    public void testIsExistClient_Success() throws IsExistClientException {
        when(clientRepository.existsByEmail(anyString())).thenReturn(true);
        boolean client = clientService.isExistClient(anyString());
        assertTrue(client);
    }

    @Test
    public void testIsExistClient_ClientNotFound() throws IsExistClientException {
        when(clientRepository.existsByEmail(anyString())).thenReturn(false);
        boolean client = clientService.isExistClient(anyString());
        assertFalse(client);
    }
    @Test
    public void testSaveClient_Success() throws SaveClientException {
        Client client = new Client();
        when(clientRepository.save(client)).thenReturn(client);
        Client savedClient = clientService.saveClient(client);
        assertNotNull(savedClient);
    }

    @Test
    public void testSaveClient_IllegalArgumentException() {
        Client client = new Client();
        when(clientRepository.save(client)).thenThrow(IllegalArgumentException.class);
        Exception exceptionExcepted = assertThrows(SaveClientException.class, () -> clientService.saveClient(client));
        assertEquals(IllegalArgumentException.class, exceptionExcepted.getCause().getClass());
    }
}
