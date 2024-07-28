package com.thery.paymybuddy.Exceptions;

import com.thery.paymybuddy.constants.MessageExceptionConstants;

import static com.thery.paymybuddy.constants.MessageExceptionConstants.*;

/**
 * Exception class for handling transaction-related service errors.
 */
public class TransactionServiceException extends Exception {

    /**
     * Constructs a new TransactionServiceException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause   The cause (which is saved for later retrieval by the getCause() method)
     */
    public TransactionServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new TransactionServiceException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method)
     */
    public TransactionServiceException(String message) {
        super(message);
    }

    /**
     * Exception class for errors occurring during retrieval of general transfer details.
     */
    public static class GetTransferredGeneralDetailException extends TransactionServiceException {

        /**
         * Constructs a new GetTransferredGeneralDetailException with the specified cause.
         *
         * @param cause The cause of this exception
         */
        public GetTransferredGeneralDetailException(Throwable cause) {
            super(GET_GENERAL_TRANSFER_DETAIL_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception class for errors occurring during the execution of a transfer operation.
     */
    public static class DoTransferException extends TransactionServiceException {

        /**
         * Constructs a new DoTransferException with the specified cause.
         *
         * @param cause The cause of this exception
         */
        public DoTransferException(Throwable cause) {
            super(DO_TRANSFER_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }
    /**
     * Exception class for errors occurring during the execution of a transfer operation with negative balance.
     */
    public static class isFundAvailableException extends TransactionServiceException {

        /**
         * Constructs a new TransactionServiceException with the specified detail message.
         */
        public isFundAvailableException() {
            super(MessageExceptionConstants.IS_FUND_AVAILABLE_EXCEPTION);
        }
    }

    /**
     * Exception class for errors occurring during the execution of a transfer because for transferring fund you need to be friend with the receiver .
     */
    public static class isTransactionBetweenFriendException extends TransactionServiceException {
        /**
         * Constructs a new isTransactionBetweenFriendException with the specified detail message.
         */
        public isTransactionBetweenFriendException() {
            super(MessageExceptionConstants.IS_TRANSACTION_BETWEEN_FRIEND_EXCEPTION);
        }
    }
    /**
     * Exception class for errors occurring during the aggregation of necessary information that client need to start transfer
     */
    public static class AggregationNecessaryInfoForTransferResponseException extends TransactionServiceException {
        /**
         * Constructs a new AggregationNecessaryInfoForTransferResponseException with the specified detail message.
         */
        public AggregationNecessaryInfoForTransferResponseException() {
            super(MessageExceptionConstants.AGGREGATION_NECESSARY_INFO_FOR_TRANSFER_RESPONSE_EXCEPTION);
        }
    }

    // Additional specific exceptions can be added as needed for other methods.
}
