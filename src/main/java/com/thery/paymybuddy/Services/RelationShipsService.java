package com.thery.paymybuddy.Services;

import com.thery.paymybuddy.dto.AddRelationShipsRequest;
import com.thery.paymybuddy.dto.AddRelationShipsResponse;
import com.thery.paymybuddy.dto.RelationShipsDetailForTransferResponse;
import com.thery.paymybuddy.repository.ClientRelationshipsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import static com.thery.paymybuddy.Exceptions.RelationShipsServiceException.*;

/**
 * Service for managing relationships between clients.
 */
@Service
public class RelationShipsService {

    private static final Logger logger = LogManager.getLogger(RelationShipsService.class);

    private final ClientRelationshipsRepository clientRelationshipsRepository;

    public RelationShipsService(ClientRelationshipsRepository clientRelationshipsRepository) {
        this.clientRelationshipsRepository = clientRelationshipsRepository;
    }

    /**
     * Adds a new relationship.
     *
     * @param addRelationShips the DTO containing the details of the relationship to add
     * @return a DTO indicating the success of the operation
     * @throws AddRelationShipsException if an error occurs while adding the relationship
     */
    public AddRelationShipsResponse addRelationShips(AddRelationShipsRequest addRelationShips) throws AddRelationShipsException {
        logger.debug("Adding new relationship: {}", addRelationShips);
        try {
            // Perform the relationship addition logic here
            AddRelationShipsResponse result = new AddRelationShipsResponse();
            logger.info("Successfully added new relationship.");
            return result;
        } catch (Exception e) {
            logger.error("Error while adding relationship: {}", e.getMessage());
            throw new AddRelationShipsException(e);
        }
    }

    /**
     * Retrieves the details of relationships for transfer.
     *
     * @return a DTO containing the details of relationships for transfer
     * @throws RelationShipsDetailForTransferException if an error occurs while retrieving the details
     */
    public RelationShipsDetailForTransferResponse relationShipsDetailForTransfer() throws RelationShipsDetailForTransferException {
        logger.debug("Retrieving relationship details for transfer.");
        try {
            // Perform the relationship detail retrieval logic here
            RelationShipsDetailForTransferResponse result = new RelationShipsDetailForTransferResponse();
            logger.info("Successfully retrieved relationship details for transfer.");
            return result;
        } catch (Exception e) {
            logger.error("Error while retrieving relationship details for transfer: {}", e.getMessage());
            throw new RelationShipsDetailForTransferException(e);
        }
    }
}
