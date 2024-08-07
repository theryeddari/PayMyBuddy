package com.thery.paymybuddy.controller;

import com.thery.paymybuddy.dto.ProfileClientChangeRequest;
import com.thery.paymybuddy.dto.ProfileClientChangeResponse;
import com.thery.paymybuddy.dto.ProfileClientResponse;
import com.thery.paymybuddy.dto.SavingClientResponse;
import com.thery.paymybuddy.service.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.thery.paymybuddy.constant.MessagesServicesConstants.CHANGE_PROFILE_SUCCESS;
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
        ProfileClientChangeRequest profileClientChangeRequest = new ProfileClientChangeRequest("Alice", "alice@gmail.com", "alicep");
        ProfileClientChangeResponse profileClientChangeResponse = new ProfileClientChangeResponse(CHANGE_PROFILE_SUCCESS);

        when(clientService.changeProfile(any(ProfileClientChangeRequest.class))).thenReturn(profileClientChangeResponse);

        ProfileClientChangeResponse result = clientController.changeProfile(profileClientChangeRequest);

        assertEquals(profileClientChangeResponse, result);
        verify(clientService).changeProfile(profileClientChangeRequest);
        assertEquals(profileClientChangeResponse, result);
    }

    @Test
    void getSavingClient_success() throws Exception {
        SavingClientResponse savingClientResponse = new SavingClientResponse(1000.0);

        when(clientService.getSavingClient()).thenReturn(savingClientResponse);

        SavingClientResponse result = clientController.getSavingClient();

        verify(clientService).getSavingClient();
        assertEquals(savingClientResponse, result);
    }
}
