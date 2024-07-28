package com.thery.paymybuddy.services;

import static com.thery.paymybuddy.Exceptions.InformationOnContextUtilsException.*;
import static com.thery.paymybuddy.Exceptions.TransactionServiceException.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.thery.paymybuddy.Services.TransactionService;
import com.thery.paymybuddy.dto.DoTransferRequest;
import com.thery.paymybuddy.dto.DoTransferResponse;
import com.thery.paymybuddy.dto.TransferredGeneralDetailDTO;
import com.thery.paymybuddy.dto.TransferredGeneralDetailResponse;
import com.thery.paymybuddy.models.Client;
import com.thery.paymybuddy.models.Transaction;
import com.thery.paymybuddy.repository.TransactionRepository;
import com.thery.paymybuddy.utils.InformationOnContextUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;


@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void testGetTransferredGeneralDetail_Success() throws GetTransferredGeneralDetailException {
        TransferredGeneralDetailDTO transferredGeneralDetailDTO = new TransferredGeneralDetailDTO("test@example.com", "description test", 10.0);
        List<TransferredGeneralDetailDTO> transferredGeneralDetailDTOList = List.of(transferredGeneralDetailDTO);
        TransferredGeneralDetailResponse transferredGeneralDetailResponse = new TransferredGeneralDetailResponse(transferredGeneralDetailDTOList);


        try (MockedStatic<InformationOnContextUtils> informationOnContextUtilsMockedStatic = mockStatic(InformationOnContextUtils.class)) {
            // Mock the static method
            informationOnContextUtilsMockedStatic.when(InformationOnContextUtils::getIdClientFromContext).thenReturn("1");

            Transaction transaction = mock(Transaction.class);
            List<Transaction> transactionList = List.of(transaction);

            when(transactionRepository.findBySender_Id(1L)).thenReturn(transactionList);

            Client client = mock(Client.class);
            when(transaction.getReceiver()).thenReturn(client);
            when(client.getEmail()).thenReturn("test@example.com");
            when(transaction.getAmount()).thenReturn(10.0);
            when(transaction.getDescription()).thenReturn("description test");

            TransferredGeneralDetailResponse result = transactionService.getTransferredGeneralDetail();

            assertEquals(transferredGeneralDetailResponse.getListTransferredSuccesses().size(), result.getListTransferredSuccesses().size());
            assertEquals(transferredGeneralDetailResponse.getListTransferredSuccesses().getFirst().getAmount(), result.getListTransferredSuccesses().getFirst().getAmount());
            assertEquals(transferredGeneralDetailResponse.getListTransferredSuccesses().getFirst().getDescription(), result.getListTransferredSuccesses().getFirst().getDescription());
            assertEquals(transferredGeneralDetailResponse.getListTransferredSuccesses().getFirst().getReceiverEmail(), result.getListTransferredSuccesses().getFirst().getReceiverEmail());

        }
    }

    @Test
    public void testGetTransferredGeneralDetail_Exception() {
        Exception exception = assertThrows(GetTransferredGeneralDetailException.class, () -> transactionService.getTransferredGeneralDetail());
        assertEquals(GetIdClientFromContextException.class,exception.getCause().getClass());
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