package com.thery.paymybuddy.service;

import com.thery.paymybuddy.dto.*;
import com.thery.paymybuddy.exception.RelationShipsServiceException;
import com.thery.paymybuddy.model.Client;
import com.thery.paymybuddy.model.Transaction;
import com.thery.paymybuddy.repository.TransactionRepository;
import com.thery.paymybuddy.util.InformationOnContextUtils;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static com.thery.paymybuddy.constant.MessagesServicesConstants.TRANSFER_SUCCESS;
import static com.thery.paymybuddy.exception.TransactionServiceException.*;

/**
 * Service class for handling transactions.
 */
@Service
public class TransactionService {

    private static final Logger logger = LogManager.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;
    private final ClientService clientService;
    private final RelationShipsService relationshipsService;

    /**
     * Constructor for TransactionService.
     *
     * @param transactionRepository the transaction repository
     */
    public TransactionService(TransactionRepository transactionRepository, ClientService clientService, RelationShipsService relationshipsService) {
        this.transactionRepository = transactionRepository;
        this.clientService = clientService;
        this.relationshipsService = relationshipsService;
    }

    /**
     * Retrieves general transfer details.
     *
     * @return TransferredGeneralDetailSuccessDTO containing the general transfer details
     * @throws GetTransferredGeneralDetailException if an error occurs while retrieving the details
     */
    @Transactional
    public TransferredGeneralDetailResponse getTransferredGeneralDetail() throws GetTransferredGeneralDetailException {
        try {
            long clientId = Long.parseLong(InformationOnContextUtils.getIdClientFromContext());
            List<Transaction> transactionsList = transactionRepository.findBySender_Id(clientId);
            List<TransferredGeneralDetailDTO> transferredGeneralDetailDTOList = new ArrayList<>();
            transactionsList.forEach(transaction -> transferredGeneralDetailDTOList.add(new TransferredGeneralDetailDTO(transaction.getReceiver().getEmail(), transaction.getDescription(), transaction.getAmount())));
            logger.info("Retrieving general transfer details.");
            return new TransferredGeneralDetailResponse(transferredGeneralDetailDTOList);
        } catch (Exception e) {
            logger.error("Error while retrieving general transfer details.", e);
            throw new GetTransferredGeneralDetailException(e);
        }
    }

    /**
     * Performs a transfer.
     *
     * @param doTransferRequest the details for transferring
     * @return TransferringSuccessDTO containing the result of the transfer
     * @throws DoTransferException if an error occurs during the transfer
     */
    @Transactional
    public DoTransferResponse doTransfer(DoTransferRequest doTransferRequest) throws DoTransferException {
        try {
            long clientId = Long.parseLong(InformationOnContextUtils.getIdClientFromContext());
            Client sender = clientService.findById(clientId);
            isFundAvailableUpdateClient(sender, doTransferRequest.getAmount());

            Client receiver = clientService.findByEmail(doTransferRequest.getReceiverEmail());
            isTransactionBetweenFriend(receiver.getEmail());
            updateClientReceiverFund(receiver, doTransferRequest.getAmount());


            Transaction transaction = new Transaction();
            transaction.setDescription(doTransferRequest.getDescription());
            transaction.setAmount(doTransferRequest.getAmount());
            transaction.setReceiver(receiver);
            transaction.setSender(sender);
            transactionRepository.save(transaction);


            logger.info("Performing transfer with details: {}", doTransferRequest);
            return new DoTransferResponse(TRANSFER_SUCCESS);
        } catch (Exception e) {
            logger.error("Error while performing transfer.", e);
            throw new DoTransferException(e);
        }
    }

    private void updateClientReceiverFund(Client receiver, Double amount) {
        BigDecimal currentSaving = BigDecimal.valueOf(receiver.getSaving());
        BigDecimal amountToTransfer = new BigDecimal(amount);

        BigDecimal newSaving = currentSaving.add(amountToTransfer);

        newSaving = newSaving.setScale(2, RoundingMode.HALF_UP);

        receiver.setSaving(newSaving.doubleValue());
    }

    private void isFundAvailableUpdateClient(Client sender, double amountTransfer) throws isFundAvailableException {
        BigDecimal saving = BigDecimal.valueOf(sender.getSaving());
        BigDecimal transferAmount = new BigDecimal(amountTransfer);

        BigDecimal balance = saving.subtract(transferAmount);

        balance = balance.setScale(2, RoundingMode.HALF_UP);

        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new isFundAvailableException();
        }
        sender.setSaving(balance.doubleValue());
    }

    private void isTransactionBetweenFriend(String friendEmail) throws RelationShipsServiceException.RelationShipsDetailForTransferException, isTransactionBetweenFriendException {
        if (!relationshipsService.relationShipsDetailForTransfer().getListFriendsRelationShipsEmail().contains(friendEmail)) {
            throw new isTransactionBetweenFriendException();
        }
    }

    @Transactional
    public AggregationNecessaryInfoForTransferResponse aggregationNecessaryInfoForTransfer() throws AggregationNecessaryInfoForTransferResponseException {
        try {
            SavingClientResponse savingClientResponse = clientService.getSavingClient();
            RelationShipsDetailForTransferResponse relationShipsDetailForTransferResponse = relationshipsService.relationShipsDetailForTransfer();
            TransferredGeneralDetailResponse transferredGeneralDetailResponse = getTransferredGeneralDetail();

            return new AggregationNecessaryInfoForTransferResponse(transferredGeneralDetailResponse, relationShipsDetailForTransferResponse, savingClientResponse);
        } catch (Exception e) {
            throw new AggregationNecessaryInfoForTransferResponseException();
        }
    }
}
