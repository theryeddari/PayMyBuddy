package com.thery.paymybuddy.controller;

import com.thery.paymybuddy.exception.ClientServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.thery.paymybuddy.constant.MessageExceptionConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientControllerAdviceTest {

    private final ClientControllerAdvice advice = new ClientControllerAdvice();

    @Test
    public void testHandleGetProfileServiceException() {
        ClientServiceException.GetProfileException ex = new ClientServiceException.GetProfileException(new RuntimeException());

        ResponseEntity<String> response = advice.handleGetProfileServiceException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(GET_PROFILE_EXCEPTION, response.getBody());
    }

    @Test
    public void testHandleChangeProfileException() {
        ClientServiceException.ChangeProfileException ex = new ClientServiceException.ChangeProfileException(new RuntimeException());

        ResponseEntity<String> response = advice.handleChangeProfileException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(CHANGE_PROFILE_EXCEPTION, response.getBody());
    }

    @Test
    public void testHandleGetSavingClientException() {
        ClientServiceException.GetSavingClientException ex = new ClientServiceException.GetSavingClientException(new RuntimeException());

        ResponseEntity<String> response = advice.handleGetSavingClientException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(GET_SAVING_CLIENT_EXCEPTION, response.getBody());
    }
}
