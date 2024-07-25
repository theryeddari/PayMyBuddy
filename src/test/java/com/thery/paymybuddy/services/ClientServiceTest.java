package com.thery.paymybuddy.services;

import static com.thery.paymybuddy.Exceptions.ClientServiceException.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.thery.paymybuddy.Services.ClientService;
import com.thery.paymybuddy.dto.ProfileClientChangeRequest;
import com.thery.paymybuddy.dto.ProfileClientChangeResponse;
import com.thery.paymybuddy.dto.ProfileClientResponse;
import com.thery.paymybuddy.dto.SavingClientResponse;
import com.thery.paymybuddy.models.Client;
import com.thery.paymybuddy.repository.ClientRepository;
import com.thery.paymybuddy.utils.InformationOnContextUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @Test
    public void testGetProfile_Success() throws GetProfileException {
        Client client = mock(Client.class);
        client.setEmail("test@example.com");
        client.setPassword("hashedPassword");
        client.setUsername("test");

        try (MockedStatic<InformationOnContextUtils> informationOnContextUtilsMockedStatic = mockStatic(InformationOnContextUtils.class)) {
            // Mock the static method
            informationOnContextUtilsMockedStatic.when(InformationOnContextUtils::getIdClientFromContext).thenReturn("1");


            when(clientRepository.findById(anyLong())).thenReturn(Optional.of(client));

            ProfileClientResponse profileClientResponse = clientService.getProfile();

            informationOnContextUtilsMockedStatic.verify(InformationOnContextUtils::getIdClientFromContext, times(1));

            assertEquals(client.getEmail(), profileClientResponse.getEmail());
            assertEquals(client.getUsername(), profileClientResponse.getUsername());
        }
    }

    @Test
    public void testGetProfile_Exception() {
        try (MockedStatic<InformationOnContextUtils> informationOnContextUtilsMockedStatic = mockStatic(InformationOnContextUtils.class)) {
            // Mock the static method
            informationOnContextUtilsMockedStatic.when(InformationOnContextUtils::getIdClientFromContext).thenReturn("1");
            when(clientRepository.findById(anyLong())).thenReturn(Optional.empty());
            Exception exception = assertThrows(GetProfileException.class, () -> clientService.getProfile());
            assertEquals(FindByIdException.class, exception.getCause().getClass());
        }
    }

    @Test
    public void testChangeProfile_Success() throws ChangeProfileException {
        try (MockedStatic<InformationOnContextUtils> informationOnContextUtilsMockedStatic = mockStatic(InformationOnContextUtils.class)) {
            // Mock the static method
            informationOnContextUtilsMockedStatic.when(InformationOnContextUtils::getIdClientFromContext).thenReturn("1");
            ProfileClientChangeRequest profileClientChangeRequest = new ProfileClientChangeRequest("robert","alice.robert@gmail.com","robertp");

            ProfileClientChangeResponse profileClientChangeResponse = clientService.changeProfile(profileClientChangeRequest);
        }
    }

    @Test
    public void testChangeProfile_Exception() throws ChangeProfileException {
        ProfileClientChangeRequest profileClientChangeRequest = new ProfileClientChangeRequest();
        assertThrows(ChangeProfileException.class, () -> clientService.changeProfile(profileClientChangeRequest));
    }

    @Test
    public void testGetSavingClient_Success() throws GetSavingClientException {
        SavingClientResponse savingClientResponse = clientService.getSavingClient();
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
