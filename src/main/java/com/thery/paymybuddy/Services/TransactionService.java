package com.thery.paymybuddy.Services;

import com.thery.paymybuddy.dto.DoTransferRequest;
import com.thery.paymybuddy.dto.DoTransferResponse;
import com.thery.paymybuddy.dto.TransferredGeneralDetailResponse;
import com.thery.paymybuddy.repository.TransactionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import static com.thery.paymybuddy.Exceptions.TransactionServiceException.*;

/**
 * Service class for handling transactions.
 */
@Service
public class TransactionService {

    private static final Logger logger = LogManager.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;

    /**
     * Constructor for TransactionService.
     *
     * @param transactionRepository the transaction repository
     */
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Retrieves general transfer details.
     *
     * @return TransferredGeneralDetailSuccessDTO containing the general transfer details
     * @throws GetGeneralTransferDetailException if an error occurs while retrieving the details
     */
    public TransferredGeneralDetailResponse getGeneralTransferDetail() throws GetGeneralTransferDetailException {
        try {
            logger.info("Retrieving general transfer details.");
            return new TransferredGeneralDetailResponse();
        } catch (Exception e) {
            logger.error("Error while retrieving general transfer details.", e);
            throw new GetGeneralTransferDetailException(e);
        }
    }

    /**
     * Performs a transfer.
     *
     * @param doTransferRequest the details for transferring
     * @return TransferringSuccessDTO containing the result of the transfer
     * @throws DoTransferException if an error occurs during the transfer
     */
    public DoTransferResponse doTransfer(DoTransferRequest doTransferRequest) throws DoTransferException {
        try {
            logger.info("Performing transfer with details: {}", doTransferRequest);
            return new DoTransferResponse();
        } catch (Exception e) {
            logger.error("Error while performing transfer.", e);
            throw new DoTransferException(e);
        }
    }
}
