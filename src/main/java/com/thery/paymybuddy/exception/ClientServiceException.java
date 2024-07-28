package com.thery.paymybuddy.exception;

import com.thery.paymybuddy.constant.MessageExceptionConstants;

import static com.thery.paymybuddy.constant.MessageExceptionConstants.*;

/**
 * Custom exception class for handling errors related to client service.
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
     * Constructs a new client service exception with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     */
    public ClientServiceException(String message) { super(message); }


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

    /**
     * Exception class for client find by email operation errors.
     */
    public static class FindByEmailException extends ClientServiceException {
        /**
         * Constructs a FindByEmailException with the cause of the exception.
         *
         * @param cause The cause of the exception
         */
        public FindByEmailException(Exception cause) {
            super(CLIENT_FIND_BY_EMAIL_EXCEPTION + MORE_INFO + " " + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception class for client not found operation errors.
     */
    public static class ClientNotFoundException extends ClientServiceException {
        /**
         * Constructs a ClientNotFoundException with the cause of the exception.
         */
        public ClientNotFoundException() {
            super(CLIENT_NOT_FOUND_EXCEPTION);
        }
    }

    /**
     * Exception class for save client operation errors.
     */
    public static class SaveClientException extends ClientServiceException {
        /**
         * Constructs a SaveClientException with the cause of the exception.
         *
         * @param cause The cause of the exception
         */
        public SaveClientException(Exception cause) {
            super(CLIENT_BACKUP_EXCEPTION + MORE_INFO + " " + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }
    /**
     * Exception class for exist check client operation errors.
     */
    public static class IsExistClientException extends ClientServiceException {
        /**
         * Constructs a IsExistClientException with the cause of the exception.
         *
         * @param cause The cause of the exception
         */
        public IsExistClientException(Exception cause) {
            super(CLIENT_IS_EXIST_EXCEPTION + MORE_INFO + " " + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }
    /**
     * Exception class for client find operation errors.
     */
    public static class FindByIdException extends ClientServiceException {
        /**
         * Constructs a FindByEmailException with the cause of the exception.
         *
         * @param cause The cause of the exception
         */
        public FindByIdException(Exception cause) {
            super(CLIENT_FIND_BY_ID_EXCEPTION + MORE_INFO + " " + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception class for incoherence not found authenticated client operation errors.
     */
    public static class AuthenticatedClientNotFoundException extends ClientServiceException {
        /**
         * Constructs a IsExistClientException
         */
        public AuthenticatedClientNotFoundException() {
            super(MessageExceptionConstants.AUTHENTICATE_CLIENT_NOT_FOUND_EXCEPTION);
        }
    }


}
