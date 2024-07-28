package com.thery.paymybuddy.controller;

import com.thery.paymybuddy.Services.TransactionService;
import com.thery.paymybuddy.dto.DoTransferRequest;
import com.thery.paymybuddy.dto.DoTransferResponse;
import com.thery.paymybuddy.dto.TransferredGeneralDetailResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.thery.paymybuddy.Exceptions.TransactionServiceException.*;

/**
 * Controller class for managing transfer operations.
 */
@RestController
@RequestMapping("/api/fr/client/dashboard")
public class TransactionController {

    private static final Logger logger = LogManager.getLogger(TransactionController.class);

    @Autowired
    TransactionService transactionService;

    /**
     * GET endpoint to fetch transfer details.
     *
     * @return TransferredGeneralDetailResponse object representing transfer details.
     */
    @GetMapping("/transaction")
    @ResponseStatus(HttpStatus.OK)
    public TransferredGeneralDetailResponse getTransferredGeneralDetail() throws GetTransferredGeneralDetailException {
        logger.info("Fetching general transfer detail");
        TransferredGeneralDetailResponse transferDetail = transactionService.getTransferredGeneralDetail();
        logger.info("General transfer detail fetched successfully");
        return transferDetail;
    }

    /**
     * POST endpoint to initiate a transfer.
     *
     * @param detailOnTransferDTO DetailForTransferringDTO object containing transfer details.
     * @return TransferringSuccessDTO object indicating the success of the transfer.
     */
    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public DoTransferResponse doTransfer(@RequestBody DoTransferRequest detailOnTransferDTO) throws DoTransferException {
        logger.info("Initiating transfer");
        DoTransferResponse transferResult = transactionService.doTransfer(detailOnTransferDTO);
        logger.info("Transfer completed successfully");
        return transferResult;
    }
}
