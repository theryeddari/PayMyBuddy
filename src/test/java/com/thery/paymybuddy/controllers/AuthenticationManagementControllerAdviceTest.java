package com.thery.paymybuddy.controllers;

import com.thery.paymybuddy.controller.AuthenticationManagementControllerAdvice;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.thery.paymybuddy.Exceptions.AuthenticationManagementServiceException.*;
import static com.thery.paymybuddy.constants.MessageExceptionConstants.*;
import static com.thery.paymybuddy.constants.MessageExceptionConstants.CLIENT_ALREADY_EXISTS_EXCEPTION;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthenticationManagementControllerAdviceTest {

        private final AuthenticationManagementControllerAdvice advice = new AuthenticationManagementControllerAdvice();

        @Test
        public void testHandleSignUpClientException_ClientAlreadyExistException() {
            SignUpClientException ex = new SignUpClientException(new ClientAlreadyExistException());

            ResponseEntity<String> response = advice.handleSignUpClientException(ex);

            assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
            assertEquals(CLIENT_ALREADY_EXISTS_EXCEPTION, response.getBody());
        }

        @Test
        public void testHandleSignUpClientException_GenericException() {
            SignUpClientException ex = new SignUpClientException(new RuntimeException());

            ResponseEntity<String> response = advice.handleSignUpClientException(ex);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals(SIGN_UP_CLIENT_EXCEPTION, response.getBody());
        }

        @Test
        public void testHandleSignInClient_Exception() {
            SignInClientException ex = new SignInClientException(new RuntimeException());

            ResponseEntity<String> response = advice.handleSignInClientException(ex);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals(SIGN_IN_CLIENT_EXCEPTION, response.getBody());
        }
    @Test
    public void testHandleSignInClient_ClientNotFoundException() {
        String email = "test@exemple.com";
        SignInClientException ex = new SignInClientException(new ClientNotFoundException(email));

        ResponseEntity<String> response = advice.handleSignInClientException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(CLIENT_NOT_FOUND_EXCEPTION + email, response.getBody());
    }


        @Test
        public void testHandleLogOutClientException() {
            LogOutClientException ex = new LogOutClientException(new RuntimeException());

            ResponseEntity<String> response = advice.handleLogOutClientException(ex);

            assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
            assertEquals(LOG_OUT_CLIENT_EXCEPTION, response.getBody());
        }
    }

