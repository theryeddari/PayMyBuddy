package com.thery.paymybuddy.Exceptions;

import static com.thery.paymybuddy.constants.MessageExceptionConstants.*;

/**
 * Custom exception class for handling errors related to client services.
 */
public class ClientServiceException extends Exception {

    /**
     * Constructs a new client service exception with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   the cause (which is saved for later retrieval by the getCause() method).
     */
    public ClientServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception class specific to errors encountered in retrieving a client's profile.
     */
    public static class GetProfileException extends ClientServiceException {

        /**
         * Constructs a new GetProfileException with the specified cause.
         *
         * @param cause the cause of the exception.
         */
        public GetProfileException(Throwable cause) {
            super(GET_PROFILE_EXCEPTION + cause.getClass() + MORE_INFO + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception class specific to errors encountered in changing a client's profile.
     */
    public static class ChangeProfileException extends ClientServiceException {

        /**
         * Constructs a new ChangeProfileException with the specified cause.
         *
         * @param cause the cause of the exception.
         */
        public ChangeProfileException(Exception cause) {
            super(CHANGE_PROFILE_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception class specific to errors encountered in retrieving a client's savings.
     */
    public static class GetSavingClientException extends ClientServiceException {

        /**
         * Constructs a new GetSavingClientException with the specified cause.
         *
         * @param cause the cause of the exception.
         */
        public GetSavingClientException(Exception cause) {
            super(GET_SAVING_CLIENT_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

}
