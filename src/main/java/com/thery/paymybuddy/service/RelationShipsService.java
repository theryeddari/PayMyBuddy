package com.thery.paymybuddy.service;

import com.thery.paymybuddy.dto.AddRelationShipsRequest;
import com.thery.paymybuddy.dto.AddRelationShipsResponse;
import com.thery.paymybuddy.dto.RelationShipsDetailForTransferResponse;
import com.thery.paymybuddy.model.Client;
import com.thery.paymybuddy.model.ClientRelationships;
import com.thery.paymybuddy.repository.ClientRelationshipsRepository;
import com.thery.paymybuddy.util.InformationOnContextUtils;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.thery.paymybuddy.constant.MessagesServicesConstants.ADD_RELATION_SUCCESS;
import static com.thery.paymybuddy.exception.RelationShipsServiceException.*;

/**
 * Service for managing relationships between clients.
 */
@Service
public class RelationShipsService {

    private static final Logger logger = LogManager.getLogger(RelationShipsService.class);

    private final ClientRelationshipsRepository clientRelationshipsRepository;
    private final ClientService clientService;

    public RelationShipsService(ClientRelationshipsRepository clientRelationshipsRepository, ClientService clientService) {
        this.clientRelationshipsRepository = clientRelationshipsRepository;
        this.clientService = clientService;
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
            long clientId = Long.parseLong(InformationOnContextUtils.getIdClientFromContext());
            if (clientRelationshipsRepository.existsClientRelationshipsByClient_idAndFriendEmail(clientId, addRelationShips.getEmail())) {
                throw new RelationshipsAlreadyExistException();
            }
            Client friend = clientService.findByEmail(addRelationShips.getEmail());
            if (friend.getId() == clientId) {
                throw new RelationshipsAlreadyExistException.SelfOrientedRelationshipException();
            }
            Client client = clientService.findById(clientId);
            ClientRelationships clientRelationships = new ClientRelationships();
            clientRelationships.setFriend(friend);
            clientRelationships.setClient(client);

            clientRelationshipsRepository.save(clientRelationships);

            AddRelationShipsResponse result = new AddRelationShipsResponse(ADD_RELATION_SUCCESS);

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
    @Transactional
    public RelationShipsDetailForTransferResponse relationShipsDetailForTransfer() throws RelationShipsDetailForTransferException {
        logger.debug("Retrieving relationship details for transfer.");
        try {
            long clientId = Long.parseLong(InformationOnContextUtils.getIdClientFromContext());

            List<ClientRelationships> listFriendClientRelationships = clientRelationshipsRepository.findClientRelationshipsByClientId(clientId);

            List<String> listEmailFriends = listFriendClientRelationships.stream()
                    .map(ClientRelationships::getFriend)
                    .map(Client::getEmail)
                    .toList();
            RelationShipsDetailForTransferResponse result = new RelationShipsDetailForTransferResponse(listEmailFriends);
            logger.info("Successfully retrieved relationship details for transfer.");
            return result;
        } catch (Exception e) {
            logger.error("Error while retrieving relationship details for transfer: {}", e.getMessage());
            throw new RelationShipsDetailForTransferException(e);
        }
    }
}
