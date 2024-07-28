package com.thery.paymybuddy.exception;

import static com.thery.paymybuddy.constant.MessageExceptionConstants.*;

/**
 * Exception class for handling transaction-related service errors.
 */
public class JwtClientServiceConfigException extends Exception {

    /**
     * Constructs a new JwtClientServiceConfigException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause   The cause (which is saved for later retrieval by the getCause() method)
     */
    public JwtClientServiceConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Exception class for errors occurring during generate of Jwt.
     */
    public static class GenerateTokenConfigExceptionClient extends JwtClientServiceConfigException {

        /**
         * Constructs a new GenerateTokenConfigExceptionClient with the specified cause.
         *
         * @param cause The cause of this exception
         */
        public GenerateTokenConfigExceptionClient(Throwable cause) {
            super(GENERATE_TOKEN_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    // Additional specific exceptions can be added as needed for other methods.
}
