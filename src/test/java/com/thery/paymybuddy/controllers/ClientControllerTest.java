package com.thery.paymybuddy.controllers;

import com.thery.paymybuddy.Services.ClientService;
import com.thery.paymybuddy.controller.ClientController;
import com.thery.paymybuddy.dto.ProfileClientChangeRequest;
import com.thery.paymybuddy.dto.ProfileClientChangeResponse;
import com.thery.paymybuddy.dto.ProfileClientResponse;
import com.thery.paymybuddy.dto.SavingClientResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @InjectMocks
    ClientController clientController;

    @Mock
    private ClientService clientService;

    @Test
    void getProfile_success() throws Exception {

        ProfileClientResponse ProfileClientResponse = new ProfileClientResponse("Alice", "alice@gmail.com");

        when(clientService.getProfile()).thenReturn(ProfileClientResponse);


        ProfileClientResponse result = clientController.getProfile();


        assertEquals(ProfileClientResponse, result);
        verify(clientService).getProfile();
    }

    @Test
    void changeProfile_success() throws Exception {
        ProfileClientChangeRequest ProfileClientChangeRequest = new ProfileClientChangeRequest("Alice", "alice@gmail.com","alicep");
        ProfileClientChangeResponse ProfileClientChangeResponse = new ProfileClientChangeResponse("Alice", "alice@gmail.com","Profile successful changed");

        when(clientService.changeProfile(any(ProfileClientChangeRequest.class))).thenReturn(ProfileClientChangeResponse);

        ProfileClientChangeResponse result = clientController.changeProfile(ProfileClientChangeRequest);

        assertEquals(ProfileClientChangeResponse, result);
        verify(clientService).changeProfile(any(ProfileClientChangeRequest.class));
    }

    @Test
    void getSavingClient_success() throws Exception {
        SavingClientResponse SavingClientResponse = new SavingClientResponse(1000.0);

        when(clientService.getSavingClient()).thenReturn(SavingClientResponse);

        SavingClientResponse result = clientController.getSavingClient();

        assertEquals(SavingClientResponse, result);
        verify(clientService).getSavingClient();
    }
}
