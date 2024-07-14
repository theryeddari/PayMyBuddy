package com.thery.paymybuddy.controllers;

import com.thery.paymybuddy.Services.AuthenticationManagementService;
import com.thery.paymybuddy.controller.AuthenticationManagementController;
import com.thery.paymybuddy.dto.*;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

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
        SignUpRequest SignUpRequest = new SignUpRequest("Alice","alice@gmail.com","alicep");
        SignUpResponse SignUpResponse = new SignUpResponse("Alice","alice@gmail.com");

        when(authenticationManagementService.signUpClient(any(SignUpRequest.class))).thenReturn(SignUpResponse);

        SignUpResponse result = authenticationManagementController.signUpClient(SignUpRequest);

        assertEquals(SignUpResponse, result);
        verify(authenticationManagementService).signUpClient(any(SignUpRequest.class));
    }

    @Test
    void signInClient_success() throws Exception {
        SignInRequest SignInRequest = new SignInRequest("alice@gmail.com","alicep");
        SignInResponse SignInResponse = new SignInResponse("Welcome new Buddy");
        SignInResponseDTO signInResponseDTO = new SignInResponseDTO("jwt-token", SignInResponse);

        when(authenticationManagementService.signInClient(any(SignInRequest.class))).thenReturn(signInResponseDTO);

        ResponseEntity<SignInResponse> result = authenticationManagementController.signInClient(SignInRequest);

        assertEquals(SignInResponse, result.getBody());
        assertEquals(List.of("Bearer jwt-token"), result.getHeaders().get("Authorization"));
        assertEquals("Welcome new Buddy", Objects.requireNonNull(result.getBody()).getWelcomingMessage());

        verify(authenticationManagementService).signInClient(any(SignInRequest.class));
    }

    @Test
    void logOutClient_success() throws Exception {
        LogOutResponse LogOutResponse = new LogOutResponse("See you later Alice");
        when(authenticationManagementService.logOutClient()).thenReturn(LogOutResponse);

        LogOutResponse result = authenticationManagementController.logOutClient();

        assertEquals(LogOutResponse, result);
        verify(authenticationManagementService).logOutClient();
    }
}
