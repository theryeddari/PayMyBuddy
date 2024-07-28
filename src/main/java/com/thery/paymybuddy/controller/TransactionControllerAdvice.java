package com.thery.paymybuddy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.thery.paymybuddy.Exceptions.TransactionServiceException.*;
import static com.thery.paymybuddy.constants.MessageExceptionConstants.*;


/**
 * Controller advice to handle exceptions thrown by TransactionController.
 */
@ControllerAdvice
public class TransactionControllerAdvice {

    private static final Logger logger = LogManager.getLogger(TransactionControllerAdvice.class);

    /**
     * Handles GetTransferredGeneralDetailException and returns an appropriate HTTP response.
     *
     * @param ex The exception thrown
     * @return ResponseEntity containing the error message and HTTP status code
     */
    @ExceptionHandler(GetTransferredGeneralDetailException.class)
    public ResponseEntity<String> handleGetGeneralTransferDetailException(GetTransferredGeneralDetailException ex) {
        logger.error("{}", ex.getMessage());
        return new ResponseEntity<>(GET_GENERAL_TRANSFER_DETAIL_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles DoTransferException and returns an appropriate HTTP response.
     *
     * @param ex The exception thrown
     * @return ResponseEntity containing the error message and HTTP status code
     */
    @ExceptionHandler(DoTransferException.class)
    public ResponseEntity<String> handleDoTransferException(DoTransferException ex) {
        logger.error("{}", ex.getMessage());
        return new ResponseEntity<>(DO_TRANSFER_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
