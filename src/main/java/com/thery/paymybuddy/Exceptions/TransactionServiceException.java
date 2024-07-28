package com.thery.paymybuddy.Exceptions;

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

    // Additional specific exceptions can be added as needed for other methods.
}
