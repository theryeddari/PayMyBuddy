package com.thery.paymybuddy.exception;

import static com.thery.paymybuddy.constant.MessageExceptionConstants.*;

/**
 * Exception class for handling authentication management service errors.
 */
public class AuthenticationManagementServiceException extends Exception {

    /**
     * Constructs an AuthenticationManagementServiceException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method)
     */
    public AuthenticationManagementServiceException(String message) {
        super(message);
    }

    /**
     * Constructs an AuthenticationManagementServiceException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause The cause (which is saved for later retrieval by the getCause() method)
     */
    public AuthenticationManagementServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception class for sign up client operation errors.
     */
    public static class SignUpClientException extends AuthenticationManagementServiceException {

        /**
         * Constructs a SignUpClientException with the cause of the exception.
         *
         * @param cause The cause of the exception
         */
        public SignUpClientException(Throwable cause) {
            super(SIGN_UP_CLIENT_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception class for sign in client operation errors.
     */
    public static class SignInClientException extends AuthenticationManagementServiceException {

        /**
         * Constructs a SignInClientException with the cause of the exception.
         *
         * @param cause The cause of the exception
         */
        public SignInClientException(Throwable cause) {
            super(SIGN_IN_CLIENT_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception class for log out client operation errors.
     */
    public static class LogOutClientException extends AuthenticationManagementServiceException {

        /**
         * Constructs a LogOutClientException with the cause of the exception.
         *
         * @param cause The cause of the exception
         */
        public LogOutClientException(Throwable cause) {
            super(LOG_OUT_CLIENT_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception class for client already exists errors during operations.
     */
    public static class ClientAlreadyExistException extends AuthenticationManagementServiceException {

        /**
         * Constructs a ClientAlreadyExistException with the predefined error message.
         */
        public ClientAlreadyExistException() {
            super(CLIENT_ALREADY_EXISTS_EXCEPTION);
        }
    }
}
