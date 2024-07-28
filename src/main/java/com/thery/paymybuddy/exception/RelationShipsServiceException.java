package com.thery.paymybuddy.exception;

import static com.thery.paymybuddy.constant.MessageExceptionConstants.*;

/**
 * Exception class for handling errors related to relationships service operations.
 */
public class RelationShipsServiceException extends Exception {

    /**
     * Constructs a new RelationshipsServiceException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method)
     * @param cause   The cause (which is saved for later retrieval by the getCause() method)
     */
    public RelationShipsServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new RelationshipsServiceException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method)
     */
    public RelationShipsServiceException(String message) {
        super(message);
    }


    /**
     * Exception class for handling errors specifically related to adding relationships.
     */
    public static class AddRelationShipsException extends RelationShipsServiceException {

        /**
         * Constructs a new AddRelationShipsException with the specified cause.
         *
         * @param cause The cause (which is saved for later retrieval by the getCause() method)
         */
        public AddRelationShipsException(Throwable cause) {
            super(ADD_RELATIONSHIPS_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception class for handling errors related to relationship details for transfers.
     */
    public static class RelationShipsDetailForTransferException extends RelationShipsServiceException {

        /**
         * Constructs a new RelationShipsDetailForTransferException with the specified cause.
         *
         * @param cause The cause (which is saved for later retrieval by the getCause() method)
         */
        public RelationShipsDetailForTransferException(Throwable cause) {
            super(RELATIONSHIPS_DETAIL_FOR_TRANSFER_EXCEPTION + MORE_INFO + cause.getClass() + " " + cause.getMessage(), cause);
        }
    }

    /**
     * Exception class for handling errors related to add relationship about client.
     */
    public static class RelationshipsAlreadyExistException extends RelationShipsServiceException {
        /**
         * Constructs a new RelationshipsAlreadyExistException with the specified cause.
         */
        public RelationshipsAlreadyExistException() {
            super(RELATIONSHIPS_ALREADY_EXIST_EXCEPTION);
        }

        /**
         * Exception class for handling errors related to add one self relationship.
         */
        public static class SelfOrientedRelationshipException extends RelationShipsServiceException {
            /**
             * Constructs a new RelationshipsAlreadyExistException with the specified cause.
             */
            public SelfOrientedRelationshipException() {
                super(SELF_ORIENTED_RELATIONSHIP_EXCEPTION);
            }
        }
    }

    // Additional specific exception classes for other relationship service operations can be added here

}
