package com.thery.paymybuddy.services;

import static com.thery.paymybuddy.Exceptions.TransactionServiceException.*;
import static org.junit.jupiter.api.Assertions.*;

import com.thery.paymybuddy.Services.TransactionService;
import com.thery.paymybuddy.dto.DoTransferRequest;
import com.thery.paymybuddy.dto.DoTransferResponse;
import com.thery.paymybuddy.dto.TransferredGeneralDetailResponse;
import com.thery.paymybuddy.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void testGetGeneralTransferDetail_Success() throws GetGeneralTransferDetailException {
        TransferredGeneralDetailResponse actualDTO = transactionService.getGeneralTransferDetail();
    }

    @Test
    public void testGetGeneralTransferDetail_Exception() throws GetGeneralTransferDetailException {
        assertThrows(GetGeneralTransferDetailException.class, () -> transactionService.getGeneralTransferDetail());
    }

    @Test
    public void testDoTransfer_Success() throws DoTransferException {
        DoTransferRequest doTransferRequest = new DoTransferRequest();
        DoTransferResponse doTransferResponse = transactionService.doTransfer(doTransferRequest);
        assertNotNull(doTransferResponse);
    }

    @Test
    public void testDoTransfer_Exception() throws DoTransferException {
        DoTransferRequest detailForTransferringDTO = new DoTransferRequest();
        assertThrows(DoTransferException.class, () -> transactionService.doTransfer(detailForTransferringDTO));
    }
}