package com.thery.paymybuddy.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.thery.paymybuddy.Exceptions.RelationShipsServiceException.*;
import static com.thery.paymybuddy.constants.MessageExceptionConstants.*;


/**
 * Global exception handler for Relationship related exceptions in controllers.
 */
@ControllerAdvice
public class RelationShipsControllerAdvice {

    private static final Logger logger = LogManager.getLogger(RelationShipsControllerAdvice.class);

    /**
     * Handles AddRelationShipsException and logs the error message.
     *
     * @param ex The exception instance of AddRelationShipsException.
     * @return ResponseEntity with HTTP status 500 and error message constant.
     */
    @ExceptionHandler(AddRelationShipsException.class)
    public ResponseEntity<String> handleAddRelationShipsException(AddRelationShipsException ex) {
        logger.error("{}", ex.getMessage());
        return new ResponseEntity<>(ADD_RELATIONSHIPS_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handles RelationShipsDetailForTransferException and logs the error message.
     *
     * @param ex The exception instance of RelationShipsDetailForTransferException.
     * @return ResponseEntity with HTTP status 500 and error message constant.
     */
    @ExceptionHandler(RelationShipsDetailForTransferException.class)
    public ResponseEntity<String> handleRelationShipsDetailForTransferException(RelationShipsDetailForTransferException ex) {
        logger.error("{}", ex.getMessage());
        return new ResponseEntity<>(RELATIONSHIPS_DETAIL_FOR_TRANSFER_EXCEPTION, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
