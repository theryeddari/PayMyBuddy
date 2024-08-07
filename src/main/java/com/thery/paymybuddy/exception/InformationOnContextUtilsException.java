package com.thery.paymybuddy.exception;

import static com.thery.paymybuddy.constant.MessageExceptionConstants.GET_ID_CLIENT_FROM_CONTEXT_EXCEPTION;
import static com.thery.paymybuddy.constant.MessageExceptionConstants.MORE_INFO;

/**
 * Exception class for handling information get Utils errors.
 */
public class InformationOnContextUtilsException extends Exception {
    /**
     * Constructs an AuthenticationManagementServiceException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method)
     */
    public InformationOnContextUtilsException(String message) {
        super(message);
    }

    /**
     * Constructs an AuthenticationManagementServiceException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause   The cause (which is saved for later retrieval by the getCause() method)
     */
    public InformationOnContextUtilsException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * Exception class for get id's client operation errors.
     */
    public static class GetIdClientFromContextException extends InformationOnContextUtilsException {
        /**
         * Constructs a GetIdClientFromContextException with the cause of the exception.
         *
         * @param cause The cause of the exception
         */
        public GetIdClientFromContextException(Exception cause) {
            super(GET_ID_CLIENT_FROM_CONTEXT_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }
}
