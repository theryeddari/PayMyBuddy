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

        ProfileClientResponse profileClientResponse = new ProfileClientResponse("Alice", "alice@gmail.com");

        when(clientService.getProfile()).thenReturn(profileClientResponse);


        ProfileClientResponse result = clientController.getProfile();


        assertEquals(profileClientResponse, result);
        verify(clientService).getProfile();
    }

    @Test
    void changeProfile_success() throws Exception {
        ProfileClientChangeRequest profileClientChangeRequest = new ProfileClientChangeRequest("Alice", "alice@gmail.com","alicep");
        ProfileClientChangeResponse profileClientChangeResponse = new ProfileClientChangeResponse("Profile successful changed");

        when(clientService.changeProfile(any(ProfileClientChangeRequest.class))).thenReturn(profileClientChangeResponse);

        ProfileClientChangeResponse result = clientController.changeProfile(profileClientChangeRequest);

        assertEquals(profileClientChangeResponse, result);
        verify(clientService).changeProfile(any(ProfileClientChangeRequest.class));
    }

    @Test
    void getSavingClient_success() throws Exception {
        SavingClientResponse savingClientResponse = new SavingClientResponse(1000.0);

        when(clientService.getSavingClient()).thenReturn(savingClientResponse);

        SavingClientResponse result = clientController.getSavingClient();

        assertEquals(savingClientResponse, result);
        verify(clientService).getSavingClient();
    }
}
