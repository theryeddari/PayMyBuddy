package com.thery.paymybuddy.services;

import com.thery.paymybuddy.Services.AuthenticationManagementService;
import com.thery.paymybuddy.Services.ClientService;
import com.thery.paymybuddy.configs.security.JwtClientServiceConfig;
import com.thery.paymybuddy.constants.MessagesServicesConstants;
import com.thery.paymybuddy.dto.*;
import com.thery.paymybuddy.models.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.thery.paymybuddy.Exceptions.AuthenticationManagementServiceException.*;
import static com.thery.paymybuddy.Exceptions.ClientServiceException.*;
import static com.thery.paymybuddy.constants.MessagesServicesConstants.LOG_OUT_SUCCESS;
import static com.thery.paymybuddy.constants.MessagesServicesConstants.SIGN_IN_SUCCESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationManagementServiceTest {

    @Mock
    private ClientService clientService;

    @Mock
    private PasswordEncoder clientPasswordEncoder;

    @Mock
    private JwtClientServiceConfig jwtClientServiceConfig;

    private AuthenticationManagementService authenticationManagementService;

    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() throws Exception {
        // Créez et configurez les mocks avec RETURNS_DEEP_STUBS
        AuthenticationConfiguration authenticationConfiguration = mock(AuthenticationConfiguration.class, RETURNS_DEEP_STUBS);
        authenticationManager = mock(AuthenticationManager.class, RETURNS_DEEP_STUBS);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(authenticationManager);

        this.authenticationManagementService = new AuthenticationManagementService(
                clientService,
                clientPasswordEncoder,
                authenticationConfiguration,
                jwtClientServiceConfig
        );
    }


    @Test
    public void testSignUpClient_Success() throws Exception {
        SignUpRequest newClient = new SignUpRequest("testUser", "test@example.com", "password123");

        when(clientService.isExistClient(newClient.getEmail())).thenReturn(false);
        when(clientPasswordEncoder.encode(newClient.getPassword())).thenReturn("encodedPassword");

        Client client = mock(Client.class);
        when(client.getUsername()).thenReturn("testUser");
        when(clientService.saveClient(any(Client.class))).thenReturn(client);

        SignUpResponse response = authenticationManagementService.signUpClient(newClient);

        assertNotNull(response);
        assertEquals(MessagesServicesConstants.SIGN_UP_SUCCESS, response.getMessageSuccess());
    }

    @Test
    public void testSignUpClient_Exception_ClientAlreadyExists() throws IsExistClientException {
        SignUpRequest newClient = new SignUpRequest("testUser", "test@example.com", "password123");

        when(clientService.isExistClient(newClient.getEmail())).thenReturn(true);

        Exception exception = assertThrows(SignUpClientException.class, () -> authenticationManagementService.signUpClient(newClient));
        assertEquals(ClientAlreadyExistException.class,exception.getCause().getClass());
    }

    @Test
    public void testSignInClient_Success() throws Exception {

        //input element for method and output
        SignInRequest signInRequest = new SignInRequest("alice@exemple.com", "alicep");
        SignInResponseDTO exceptedSignInResponseDto = new SignInResponseDTO("jwtToken",new SignInResponse(SIGN_IN_SUCCESS));

        Client client = mock(Client.class);
        when(client.getId()).thenReturn(1L);
        when(clientService.findByEmail(signInRequest.getEmail())).thenReturn(client);


        UsernamePasswordAuthenticationToken credential = new UsernamePasswordAuthenticationToken(1L, signInRequest.getPassword());
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(credential)).thenReturn(authentication);

        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(jwtClientServiceConfig.generateToken(any(Authentication.class))).thenReturn("jwtToken");

        SignInResponseDTO response = authenticationManagementService.signInClient(signInRequest);

        verify(securityContext).setAuthentication(authentication);

        assertNotNull(response);
        assertEquals(exceptedSignInResponseDto.getJwtToken(), response.getJwtToken());
        assertEquals(exceptedSignInResponseDto.getSignInResponse().getWelcomingMessage(), response.getSignInResponse().getWelcomingMessage());
        }

    @Test
    public void testSignInClient_Exception() throws FindByEmailException {
        SignInRequest signInRequest = new SignInRequest("test@example.com", "password123");

        when(clientService.findByEmail(signInRequest.getEmail())).thenThrow(new FindByEmailException(new ClientNotFoundException()));

        Exception exceptedException = assertThrows(SignInClientException.class, () -> authenticationManagementService.signInClient(signInRequest));
        assertEquals(FindByEmailException.class, exceptedException.getCause().getClass());
        assertEquals(ClientNotFoundException.class, exceptedException.getCause().getCause().getClass());
    }

    @Test
    public void testLogOutClientSuccess() throws Exception {
        LogOutResponse response = authenticationManagementService.logOutClient();
        assertEquals(LOG_OUT_SUCCESS, response.getGoodByeMessage());
    }

    @Test
    public void testLogOutClient_Exception() throws Exception {
        authenticationManagementService.logOutClient();
    }

}
