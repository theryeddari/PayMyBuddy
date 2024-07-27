package com.thery.paymybuddy.services;

import static com.thery.paymybuddy.Exceptions.ClientServiceException.*;
import static com.thery.paymybuddy.constants.MessagesServicesConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.thery.paymybuddy.Exceptions.InformationOnContextUtilsException.GetIdClientFromContextException;
import com.thery.paymybuddy.Services.ClientService;
import com.thery.paymybuddy.dto.ProfileClientChangeRequest;
import com.thery.paymybuddy.dto.ProfileClientChangeResponse;
import com.thery.paymybuddy.dto.ProfileClientResponse;
import com.thery.paymybuddy.dto.SavingClientResponse;
import com.thery.paymybuddy.models.Client;
import com.thery.paymybuddy.repository.ClientRepository;
import com.thery.paymybuddy.utils.InformationOnContextUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PasswordEncoder clientPasswordEncoder;

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
    public void testChangeProfile_WithoutPasswordAndEmailModification_Success() throws ChangeProfileException {
        ProfileClientChangeRequest profileClientChangeRequest = new ProfileClientChangeRequest("robert","","");

        Client client = mock(Client.class);
        client.setEmail("test@example.com");

        try (MockedStatic<InformationOnContextUtils> informationOnContextUtilsMockedStatic = mockStatic(InformationOnContextUtils.class)) {
            // Mock the static method
            informationOnContextUtilsMockedStatic.when(InformationOnContextUtils::getIdClientFromContext).thenReturn("1");

            when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
            when(clientRepository.save(any(Client.class))).thenReturn(new Client());

            ProfileClientChangeResponse profileClientChangeResponse = clientService.changeProfile(profileClientChangeRequest);

            informationOnContextUtilsMockedStatic.verify(InformationOnContextUtils::getIdClientFromContext, times(1));

            verify(client).setUsername(profileClientChangeRequest.getUsername());
            verify(client, never()).setEmail(profileClientChangeRequest.getEmail());
            verify(client, never()).setPassword(clientPasswordEncoder.encode(profileClientChangeRequest.getPassword()));

            Assertions.assertEquals(CHANGE_PROFILE_SUCCESS,profileClientChangeResponse.getMessageSuccess() );
        }
    }

    @Test
    public void testChangeProfile_WithPasswordModification_Success() throws ChangeProfileException {
        ProfileClientChangeRequest profileClientChangeRequest = new ProfileClientChangeRequest("robert","alice.robert@gmail.com","robertp");

        Client client = mock(Client.class);

        try (MockedStatic<InformationOnContextUtils> informationOnContextUtilsMockedStatic = mockStatic(InformationOnContextUtils.class)) {
            // Mock the static method
            informationOnContextUtilsMockedStatic.when(InformationOnContextUtils::getIdClientFromContext).thenReturn("1");

            when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
            when(clientPasswordEncoder.encode(anyString())).thenReturn("hashedPassword");
            when(clientRepository.save(any(Client.class))).thenReturn(new Client());

            ProfileClientChangeResponse profileClientChangeResponse = clientService.changeProfile(profileClientChangeRequest);

            informationOnContextUtilsMockedStatic.verify(InformationOnContextUtils::getIdClientFromContext, times(1));

            verify(client).setUsername(profileClientChangeRequest.getUsername());
            verify(client).setEmail(profileClientChangeRequest.getEmail());
            verify(client).setPassword(clientPasswordEncoder.encode(profileClientChangeRequest.getPassword()));
            Assertions.assertEquals(CHANGE_PROFILE_SUCCESS,profileClientChangeResponse.getMessageSuccess() );
        }
    }
    @Test
    public void testChangeProfile_Exception() {
        try (MockedStatic<InformationOnContextUtils> informationOnContextUtilsMockedStatic = mockStatic(InformationOnContextUtils.class)) {
            // Mock the static method
            informationOnContextUtilsMockedStatic.when(InformationOnContextUtils::getIdClientFromContext).thenThrow(GetIdClientFromContextException.class);

            Exception exception = assertThrows(ChangeProfileException.class, () -> clientService.changeProfile(new ProfileClientChangeRequest()));
            assertEquals(GetIdClientFromContextException.class, exception.getCause().getClass());
        }
    }

    @Test
    public void testGetSavingClient_Success() throws GetSavingClientException {
        try (MockedStatic<InformationOnContextUtils> informationOnContextUtilsMockedStatic = mockStatic(InformationOnContextUtils.class)) {
            // Mock the static method
            informationOnContextUtilsMockedStatic.when(InformationOnContextUtils::getIdClientFromContext).thenReturn("1");
            Client client = mock(Client.class);

            when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
            when(client.getSaving()).thenReturn(100.00);

            SavingClientResponse savingClientResponse = clientService.getSavingClient();

            assertEquals(100.00, savingClientResponse.getSaving());
        }
    }

    @Test
    public void testGetSavingClient_Exception() {
        Exception exception = assertThrows(GetSavingClientException.class, () -> clientService.getSavingClient());
        assertEquals(GetIdClientFromContextException.class, exception.getCause().getClass());

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
