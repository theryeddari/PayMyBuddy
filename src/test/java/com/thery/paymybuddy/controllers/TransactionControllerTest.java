package com.thery.paymybuddy.controllers;

import com.thery.paymybuddy.Exceptions.TransactionServiceException;
import com.thery.paymybuddy.Services.TransactionService;
import com.thery.paymybuddy.constants.MessagesServicesConstants;
import com.thery.paymybuddy.controller.TransactionController;
import com.thery.paymybuddy.dto.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;

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
    void getTransferredGeneralDetail_success() throws Exception {
        TransferredGeneralDetailDTO transferredGeneralDetailDTO = new TransferredGeneralDetailDTO("test@example.com", "description test", 10.0);
        List<TransferredGeneralDetailDTO> transferredGeneralDetailDTOList = List.of(transferredGeneralDetailDTO);
        TransferredGeneralDetailResponse transferredGeneralDetailResponse = new TransferredGeneralDetailResponse(transferredGeneralDetailDTOList);

        when(transactionService.getTransferredGeneralDetail()).thenReturn(transferredGeneralDetailResponse);

        TransferredGeneralDetailResponse result = transactionController.getTransferredGeneralDetail();

        verify(transactionService).getTransferredGeneralDetail();
        assertEquals(transferredGeneralDetailResponse, result);
    }

    @Test
    void doTransfer_success() throws Exception {
        DoTransferRequest doTransferRequest = new DoTransferRequest("test@example.com", "description test", 10.0);
        DoTransferResponse doTransferResponseExcepted = new DoTransferResponse(MessagesServicesConstants.TRANSFER_SUCCESS);


        when(transactionService.doTransfer(any(DoTransferRequest.class))).thenReturn(doTransferResponseExcepted);

        DoTransferResponse result = transactionController.doTransfer(doTransferRequest);

        verify(transactionService).doTransfer(any(DoTransferRequest.class));
        assertEquals(doTransferResponseExcepted, result);
    }

    @Test
    public void testAggregationNecessaryInfoForTransfer() throws TransactionServiceException.AggregationNecessaryInfoForTransferResponseException {
        SavingClientResponse savingClientResponse = new SavingClientResponse(100.00);
        List<String> emailFriendList = List.of("test@example.com");
        RelationShipsDetailForTransferResponse relationShipsDetailForTransferResponse = new RelationShipsDetailForTransferResponse(emailFriendList);
        TransferredGeneralDetailDTO transferredGeneralDetailDTO = new TransferredGeneralDetailDTO("test@example.com", "description test", 10.0);
        List<TransferredGeneralDetailDTO> transferredGeneralDetailDTOList = List.of(transferredGeneralDetailDTO);
        TransferredGeneralDetailResponse transferredGeneralDetailResponse = new TransferredGeneralDetailResponse(transferredGeneralDetailDTOList);
        AggregationNecessaryInfoForTransferResponse aggregationNecessaryInfoForTransferResponseExcepted = new AggregationNecessaryInfoForTransferResponse(transferredGeneralDetailResponse, relationShipsDetailForTransferResponse, savingClientResponse);

        when(transactionService.aggregationNecessaryInfoForTransfer()).thenReturn(aggregationNecessaryInfoForTransferResponseExcepted);

        AggregationNecessaryInfoForTransferResponse aggregationNecessaryInfoForTransferResponse = transactionController.aggregationNecessaryInfoForTransfer();

        verify(transactionService).aggregationNecessaryInfoForTransfer();
        assertEquals(aggregationNecessaryInfoForTransferResponseExcepted, aggregationNecessaryInfoForTransferResponse);
    }
}
