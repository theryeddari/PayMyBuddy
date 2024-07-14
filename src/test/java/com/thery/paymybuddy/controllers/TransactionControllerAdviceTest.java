package com.thery.paymybuddy.controllers;

import com.thery.paymybuddy.controller.TransactionControllerAdvice;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.thery.paymybuddy.Exceptions.TransactionServiceException.*;
import static com.thery.paymybuddy.constants.MessageExceptionConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionControllerAdviceTest {

    private final TransactionControllerAdvice advice = new TransactionControllerAdvice();

    @Test
    public void testHandleGetGeneralTransferDetailException() {
        GetGeneralTransferDetailException ex = new GetGeneralTransferDetailException(new RuntimeException());

        ResponseEntity<String> response = advice.handleGetGeneralTransferDetailException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(GET_GENERAL_TRANSFER_DETAIL_EXCEPTION, response.getBody());
    }

    @Test
    public void testHandleDoTransferException() {
        DoTransferException ex = new DoTransferException(new RuntimeException());

        ResponseEntity<String> response = advice.handleDoTransferException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(DO_TRANSFER_EXCEPTION, response.getBody());
    }
}
