package com.thery.paymybuddy.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.thery.paymybuddy.constant.MessageExceptionConstants.*;
import static com.thery.paymybuddy.exception.TransactionServiceException.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionControllerAdviceTest {

    private final TransactionControllerAdvice advice = new TransactionControllerAdvice();

    @Test
    public void testHandleGetTransferredGeneralDetailException() {
        GetTransferredGeneralDetailException ex = new GetTransferredGeneralDetailException(new RuntimeException());

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

    @Test
    public void testHandleDoTransfer_With_IsFundAvailableException() {
        DoTransferException ex = new DoTransferException(new isFundAvailableException());

        ResponseEntity<String> response = advice.handleDoTransferException(ex);

        assertEquals(HttpStatus.PAYMENT_REQUIRED, response.getStatusCode());
        assertEquals(DO_TRANSFER_EXCEPTION + MORE_INFO + IS_FUND_AVAILABLE_EXCEPTION, response.getBody());
    }

    @Test
    public void testHandleDoTransfer_With_IsTransactionBetweenFriendsException() {
        DoTransferException ex = new DoTransferException(new isTransactionBetweenFriendException());

        ResponseEntity<String> response = advice.handleDoTransferException(ex);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(DO_TRANSFER_EXCEPTION + MORE_INFO + IS_TRANSACTION_BETWEEN_FRIEND_EXCEPTION, response.getBody());
    }

    @Test
    public void testHandleAggregationNecessaryInfoForTransferException() {
        AggregationNecessaryInfoForTransferResponseException ex = new AggregationNecessaryInfoForTransferResponseException();

        ResponseEntity<String> response = advice.HandleAggregationNecessaryInfoForTransferException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(AGGREGATION_NECESSARY_INFO_FOR_TRANSFER_RESPONSE_EXCEPTION, response.getBody());
    }
}
