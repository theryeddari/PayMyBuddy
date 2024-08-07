package com.thery.paymybuddy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thery.paymybuddy.constant.MessagesServicesConstants;
import com.thery.paymybuddy.dto.*;
import com.thery.paymybuddy.exception.RelationShipsServiceException;
import com.thery.paymybuddy.model.Client;
import com.thery.paymybuddy.model.Transaction;
import com.thery.paymybuddy.repository.TransactionRepository;
import com.thery.paymybuddy.util.InformationOnContextUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.thery.paymybuddy.constant.MessageExceptionConstants.IS_FUND_AVAILABLE_EXCEPTION;
import static com.thery.paymybuddy.constant.MessageExceptionConstants.IS_TRANSACTION_BETWEEN_FRIEND_EXCEPTION;
import static com.thery.paymybuddy.exception.ClientServiceException.*;
import static com.thery.paymybuddy.exception.InformationOnContextUtilsException.GetIdClientFromContextException;
import static com.thery.paymybuddy.exception.TransactionServiceException.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ClientService clientService;

    @Mock
    private RelationShipsService relationShipsService;

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
        assertEquals(GetIdClientFromContextException.class, exception.getCause().getClass());
    }

    @Test
    public void testDoTransfer_Success() throws DoTransferException, FindByIdException, FindByEmailException, RelationShipsServiceException.RelationShipsDetailForTransferException {
        DoTransferRequest doTransferRequest = new DoTransferRequest("test@example.com", "description test", 10.0);
        DoTransferResponse doTransferResponseExcepted = new DoTransferResponse(MessagesServicesConstants.TRANSFER_SUCCESS);

        try (MockedStatic<InformationOnContextUtils> informationOnContextUtilsMockedStatic = mockStatic(InformationOnContextUtils.class)) {
            // Mock the static method
            informationOnContextUtilsMockedStatic.when(InformationOnContextUtils::getIdClientFromContext).thenReturn("1");

            Client sender = new Client();
            sender.setId(1L);
            //define for private méthod who check balance
            sender.setSaving(100.00);

            Client receiver = new Client();
            receiver.setEmail("test@example.com");

            when(clientService.findById(anyLong())).thenReturn(sender);
            when(clientService.findByEmail(anyString())).thenReturn(receiver);

            //stub for private method who check relationship between clientId and friend before transaction with method contain of list.
            List<String> emailFriendList = List.of("test@example.com");
            RelationShipsDetailForTransferResponse relationShipsDetailForTransferResponse = mock(RelationShipsDetailForTransferResponse.class);
            when(relationShipsService.relationShipsDetailForTransfer()).thenReturn(relationShipsDetailForTransferResponse);
            when(relationShipsDetailForTransferResponse.getListFriendsRelationShipsEmail()).thenReturn(emailFriendList);


            when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

            ArgumentCaptor<Transaction> transactionArgumentCaptor = ArgumentCaptor.forClass(Transaction.class);

            DoTransferResponse doTransferResponse = transactionService.doTransfer(doTransferRequest);

            verify(transactionRepository).save(transactionArgumentCaptor.capture());

            assertEquals(1L, transactionArgumentCaptor.getValue().getSender().getId());
            assertEquals("test@example.com", transactionArgumentCaptor.getValue().getReceiver().getEmail());
            assertEquals("description test", transactionArgumentCaptor.getValue().getDescription());
            assertEquals(10.0, transactionArgumentCaptor.getValue().getAmount());

            //check private method who subtracted amount transaction to saving of client
            assertEquals(90.00, transactionArgumentCaptor.getValue().getSender().getSaving());
            assertEquals(doTransferResponseExcepted.getMessageSuccess(), doTransferResponse.getMessageSuccess());
        }
    }

    //test good enclosing exception off doTransfer() and also  private methode isFundAvailable() indirectly
    @Test
    public void testDoTransfer_isFundAvailableUpdateClientException() throws FindByIdException {
        DoTransferRequest doTransferRequest = new DoTransferRequest("test@example.com", "description test", 10.0);
        try (MockedStatic<InformationOnContextUtils> informationOnContextUtilsMockedStatic = mockStatic(InformationOnContextUtils.class)) {
            // Mock the static method
            informationOnContextUtilsMockedStatic.when(InformationOnContextUtils::getIdClientFromContext).thenReturn("1");

            Client sender = mock(Client.class);
            when(clientService.findById(anyLong())).thenReturn(sender);
            when(sender.getSaving()).thenReturn(2.00);
            Exception exception = assertThrows(DoTransferException.class, () -> transactionService.doTransfer(doTransferRequest));
            assertEquals(isFundAvailableException.class, exception.getCause().getClass());
            assertEquals(IS_FUND_AVAILABLE_EXCEPTION, exception.getCause().getMessage());
        }
    }

    //test good enclosing exception off doTransfer() and also  private methode isFundAvailable() indirectly
    @Test
    public void testDoTransfer_isTransactionBetweenFriendException() throws FindByIdException, FindByEmailException, RelationShipsServiceException.RelationShipsDetailForTransferException {
        DoTransferRequest doTransferRequest = new DoTransferRequest("test@example.com", "description test", 10.0);
        try (MockedStatic<InformationOnContextUtils> informationOnContextUtilsMockedStatic = mockStatic(InformationOnContextUtils.class)) {
            // Mock the static method
            informationOnContextUtilsMockedStatic.when(InformationOnContextUtils::getIdClientFromContext).thenReturn("1");

            Client sender = new Client();
            sender.setId(1L);
            //define for private méthod who check balance
            sender.setSaving(100.00);

            Client receiver = new Client();
            receiver.setEmail("test@example.com");

            when(clientService.findById(anyLong())).thenReturn(sender);
            when(clientService.findByEmail(anyString())).thenReturn(receiver);

            //stub for private method who check relationship between clientId and friend before transaction with method contain of list.
            List<String> emailFriendList = new ArrayList<>();
            RelationShipsDetailForTransferResponse relationShipsDetailForTransferResponse = mock(RelationShipsDetailForTransferResponse.class);
            when(relationShipsService.relationShipsDetailForTransfer()).thenReturn(relationShipsDetailForTransferResponse);
            when(relationShipsDetailForTransferResponse.getListFriendsRelationShipsEmail()).thenReturn(emailFriendList);

            Exception exception = assertThrows(DoTransferException.class, () -> transactionService.doTransfer(doTransferRequest));
            assertEquals(isTransactionBetweenFriendException.class, exception.getCause().getClass());
            assertEquals(IS_TRANSACTION_BETWEEN_FRIEND_EXCEPTION, exception.getCause().getMessage());
        }
    }

    @Test
    public void testAggregationNecessaryInfoForTransfer() throws GetSavingClientException, RelationShipsServiceException.RelationShipsDetailForTransferException, JsonProcessingException, AggregationNecessaryInfoForTransferResponseException {
        SavingClientResponse savingClientResponse = new SavingClientResponse(100.00);
        List<String> emailFriendList = List.of("test@example.com");
        RelationShipsDetailForTransferResponse relationShipsDetailForTransferResponse = new RelationShipsDetailForTransferResponse(emailFriendList);
        TransferredGeneralDetailDTO transferredGeneralDetailDTO = new TransferredGeneralDetailDTO("test@example.com", "description test", 10.0);
        List<TransferredGeneralDetailDTO> transferredGeneralDetailDTOList = List.of(transferredGeneralDetailDTO);
        TransferredGeneralDetailResponse transferredGeneralDetailResponse = new TransferredGeneralDetailResponse(transferredGeneralDetailDTOList);
        AggregationNecessaryInfoForTransferResponse aggregationNecessaryInfoForTransferResponseExcepted = new AggregationNecessaryInfoForTransferResponse(transferredGeneralDetailResponse, relationShipsDetailForTransferResponse, savingClientResponse);

        try (MockedStatic<InformationOnContextUtils> informationOnContextUtilsMockedStatic = mockStatic(InformationOnContextUtils.class)) {
            // Mock the static method
            informationOnContextUtilsMockedStatic.when(InformationOnContextUtils::getIdClientFromContext).thenReturn("1");

            //stub necessary service and method
            when(clientService.getSavingClient()).thenReturn(savingClientResponse);
            when(relationShipsService.relationShipsDetailForTransfer()).thenReturn(relationShipsDetailForTransferResponse);

            //Concerning the method which allows me to retrieve "transferredGeneralDetailResponse",
            // being itself a method of the service tested, I cannot mock it, so I simulate its operation as if
            // the logic was in this method tested
            Transaction transaction = mock(Transaction.class);
            List<Transaction> transactionList = List.of(transaction);

            when(transactionRepository.findBySender_Id(1L)).thenReturn(transactionList);

            Client client = mock(Client.class);
            when(transaction.getReceiver()).thenReturn(client);
            when(client.getEmail()).thenReturn("test@example.com");
            when(transaction.getAmount()).thenReturn(10.0);
            when(transaction.getDescription()).thenReturn("description test");

            AggregationNecessaryInfoForTransferResponse aggregationNecessaryInfoForTransferResponse = transactionService.aggregationNecessaryInfoForTransfer();

            ObjectMapper objectMapper = new ObjectMapper();
            assertEquals(objectMapper.writeValueAsString(aggregationNecessaryInfoForTransferResponseExcepted), objectMapper.writeValueAsString(aggregationNecessaryInfoForTransferResponse));
        }
    }

    @Test
    public void testAggregationNecessaryInfoForTransfer_Exception() throws GetSavingClientException {

        try (MockedStatic<InformationOnContextUtils> informationOnContextUtilsMockedStatic = mockStatic(InformationOnContextUtils.class)) {
            // Mock the static method
            informationOnContextUtilsMockedStatic.when(InformationOnContextUtils::getIdClientFromContext).thenReturn("1");

            when(clientService.getSavingClient()).thenThrow(new RuntimeException());

            assertThrows(AggregationNecessaryInfoForTransferResponseException.class, () -> transactionService.aggregationNecessaryInfoForTransfer());
        }
    }

}