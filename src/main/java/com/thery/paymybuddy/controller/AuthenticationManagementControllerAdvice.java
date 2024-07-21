package com.thery.paymybuddy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.thery.paymybuddy.Exceptions.AuthenticationManagementServiceException.*;
import static com.thery.paymybuddy.constants.MessageExceptionConstants.*;

/**
 * Controller advice to handle exceptions thrown by AuthenticationManagementController.
 */
@ControllerAdvice
public class AuthenticationManagementControllerAdvice {

    private static final Logger logger = LogManager.getLogger(AuthenticationManagementControllerAdvice.class);

    /**
     * Handles SignUpClientException and returns an appropriate HTTP response.
     *
     * @param ex The exception instance thrown.
     * @return ResponseEntity with error message and HTTP status code.
     */
    @ExceptionHandler(SignUpClientException.class)
    public ResponseEntity<String> handleSignUpClientException(SignUpClientException ex) {
        logger.error("{}", ex.getMessage());
        if (ex.getCause() instanceof ClientAlreadyExistException) {
            return new ResponseEntity<>(ex.getCause().getMessage(), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(SIGN_UP_CLIENT_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles SignInClientException and returns an appropriate HTTP response.
     *
     * @param ex The exception instance thrown.
     * @return ResponseEntity with error message and HTTP status code.
     */
    @ExceptionHandler(SignInClientException.class)
    public ResponseEntity<String> handleSignInClientException(SignInClientException ex) {
        logger.error("{}", ex.getMessage());
        if (ex.getCause() instanceof ClientNotFoundException) {
            return new ResponseEntity<>(ex.getCause().getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(SIGN_IN_CLIENT_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles LogOutClientException and returns an appropriate HTTP response.
     *
     * @param ex The exception instance thrown.
     * @return ResponseEntity with error message and HTTP status code.
     */
    @ExceptionHandler(LogOutClientException.class)
    public ResponseEntity<String> handleLogOutClientException(LogOutClientException ex) {
        logger.error("{}", ex.getMessage());
        return new ResponseEntity<>(LOG_OUT_CLIENT_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
