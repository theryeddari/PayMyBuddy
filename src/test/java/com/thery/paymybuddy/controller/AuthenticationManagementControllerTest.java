package com.thery.paymybuddy.controller;

import com.thery.paymybuddy.service.AuthenticationManagementService;
import com.thery.paymybuddy.dto.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static com.thery.paymybuddy.constant.MessagesServicesConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public  class AuthenticationManagementControllerTest {

        @InjectMocks
        AuthenticationManagementController authenticationManagementController;

        @Mock
        private AuthenticationManagementService authenticationManagementService;



    @Test
    void signUpClient_success() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("Alice","alice@gmail.com","alicep");
        SignUpResponse signUpResponse = new SignUpResponse(SIGN_UP_SUCCESS);

        when(authenticationManagementService.signUpClient(any(SignUpRequest.class))).thenReturn(signUpResponse);

        SignUpResponse result = authenticationManagementController.signUpClient(signUpRequest);

        assertEquals(signUpResponse, result);
        verify(authenticationManagementService).signUpClient(any(SignUpRequest.class));
    }

    @Test
    void signInClient_success() throws Exception {
        SignInRequest signInRequest = new SignInRequest("alice@gmail.com","alicep");
        SignInResponse signInResponse = new SignInResponse("Welcome new Buddy");
        SignInResponseDTO signInResponseDTO = new SignInResponseDTO("jwt-token", signInResponse);

        when(authenticationManagementService.signInClient(any(SignInRequest.class))).thenReturn(signInResponseDTO);

        ResponseEntity<SignInResponse> result = authenticationManagementController.signInClient(signInRequest);

        assertEquals(signInResponse, result.getBody());
        assertEquals(List.of("Bearer jwt-token"), result.getHeaders().get("Authorization"));
        assertEquals("Welcome new Buddy", Objects.requireNonNull(result.getBody()).getWelcomingMessage());

        verify(authenticationManagementService).signInClient(any(SignInRequest.class));
    }

    @Test
    void logOutClient_success() throws Exception {
        LogOutResponse logOutResponse = new LogOutResponse("See you later Alice");
        when(authenticationManagementService.logOutClient()).thenReturn(logOutResponse);

        LogOutResponse result = authenticationManagementController.logOutClient();

        assertEquals(logOutResponse, result);
        verify(authenticationManagementService).logOutClient();
    }
}
