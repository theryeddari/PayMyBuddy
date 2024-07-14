package com.thery.paymybuddy.controllers;

import com.thery.paymybuddy.Services.TransactionService;
import com.thery.paymybuddy.controller.TransactionController;
import com.thery.paymybuddy.dto.DoTransferRequest;
import com.thery.paymybuddy.dto.DoTransferResponse;
import com.thery.paymybuddy.dto.TransferredGeneralDetailResponse;

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
public class TransactionControllerTest {

    @InjectMocks
    TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @Test
    void getGeneralTransferDetail_success() throws Exception {
        TransferredGeneralDetailResponse transferredGeneralDetailResponse = new TransferredGeneralDetailResponse(/* Add necessary details here */);

        when(transactionService.getGeneralTransferDetail()).thenReturn(transferredGeneralDetailResponse);

        TransferredGeneralDetailResponse result = transactionController.getGeneralTransferDetail();

        assertEquals(transferredGeneralDetailResponse, result);
        verify(transactionService).getGeneralTransferDetail();
    }

    @Test
    void doTransfer_success() throws Exception {
        DoTransferRequest detailForTransferringDTO = new DoTransferRequest(/* Add necessary details here */);
        DoTransferResponse doTransferResponse = new DoTransferResponse(/* Add necessary details here */);

        when(transactionService.doTransfer(any(DoTransferRequest.class))).thenReturn(doTransferResponse);

        DoTransferResponse result = transactionController.doTransfer(detailForTransferringDTO);

        assertEquals(doTransferResponse, result);
        verify(transactionService).doTransfer(any(DoTransferRequest.class));
    }
}