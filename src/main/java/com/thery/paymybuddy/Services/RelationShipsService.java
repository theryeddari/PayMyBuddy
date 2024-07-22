package com.thery.paymybuddy.Services;

import com.thery.paymybuddy.constants.MessagesServicesConstants;
import com.thery.paymybuddy.dto.AddRelationShipsRequest;
import com.thery.paymybuddy.dto.AddRelationShipsResponse;
import com.thery.paymybuddy.dto.RelationShipsDetailForTransferResponse;
import com.thery.paymybuddy.models.Client;
import com.thery.paymybuddy.models.ClientRelationships;
import com.thery.paymybuddy.repository.ClientRelationshipsRepository;
import jakarta.transaction.Transactional;
import jakarta.transaction.Transactional;
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
    private final ClientService clientService;
    private final AuthenticationManagementService authenticationManagementService;

    public RelationShipsService(ClientRelationshipsRepository clientRelationshipsRepository, ClientService clientService, AuthenticationManagementService authenticationManagementService) {
        this.clientRelationshipsRepository = clientRelationshipsRepository;
        this.clientService = clientService;
        this.authenticationManagementService = authenticationManagementService;
    }

    /**
     * Adds a new relationship.
     *
     * @param addRelationShips the DTO containing the details of the relationship to add
     * @return a DTO indicating the success of the operation
     * @throws AddRelationShipsException if an error occurs while adding the relationship
     */
    @Transactional
    public AddRelationShipsResponse addRelationShips(AddRelationShipsRequest addRelationShips) throws AddRelationShipsException {
        logger.debug("Adding new relationship: {}", addRelationShips);
        try {
            long clientId = Long.parseLong(authenticationManagementService.getIdClientFromContext());
            if(clientRelationshipsRepository.existsClientRelationshipsByClient_idAndFriendEmail(clientId, addRelationShips.getEmail())) {
                throw new RelationshipsAlreadyExistException();
            }
            Client friend = clientService.findByEmail(addRelationShips.getEmail());
            if(friend.getId() == clientId) {
                throw new RelationshipsAlreadyExistException.SelfOrientedRelationshipException();
            }
            Client client = clientService.findById(clientId);
            ClientRelationships clientRelationships = new ClientRelationships();
            clientRelationships.setFriend(friend);
            clientRelationships.setClient(client);

            ClientRelationships clientRelationshipsSaved = clientRelationshipsRepository.save(clientRelationships);

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
