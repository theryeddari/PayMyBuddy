package com.thery.paymybuddy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.thery.paymybuddy.Exceptions.ClientServiceException.*;
import static com.thery.paymybuddy.constants.MessageExceptionConstants.*;

/**
 * Controller advice to handle exceptions thrown by ClientController.
 */
@ControllerAdvice
public class ClientControllerAdvice {

    private static final Logger logger = LogManager.getLogger(ClientControllerAdvice.class);

    /**
     * Handles GetProfileException and logs the error message.
     * @param ex The exception object.
     * @return ResponseEntity with an error message and HTTP status code INTERNAL_SERVER_ERROR.
     */
    @ExceptionHandler(GetProfileException.class)
    public ResponseEntity<String> handleGetProfileServiceException(GetProfileException ex) {
        logger.error("{}", ex.getMessage());
        return new ResponseEntity<>(GET_PROFILE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles ChangeProfileException and logs the error message.
     * @param ex The exception object.
     * @return ResponseEntity with an error message and HTTP status code INTERNAL_SERVER_ERROR.
     */
    @ExceptionHandler(ChangeProfileException.class)
    public ResponseEntity<String> handleChangeProfileException(ChangeProfileException ex) {
        logger.error("{}", ex.getMessage());
        return new ResponseEntity<>(CHANGE_PROFILE_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles GetSavingClientException and logs the error message.
     * @param ex The exception object.
     * @return ResponseEntity with an error message and HTTP status code INTERNAL_SERVER_ERROR.
     */
    @ExceptionHandler(GetSavingClientException.class)
    public ResponseEntity<String> handleGetSavingClientException(GetSavingClientException ex) {
        logger.error("{}", ex.getMessage());
        return new ResponseEntity<>(GET_SAVING_CLIENT_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
