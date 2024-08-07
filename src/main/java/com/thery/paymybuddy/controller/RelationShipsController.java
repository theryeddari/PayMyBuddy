/**
 * Controller class for managing relationships between clients.
 * Handles API endpoints related to client relationships.
 */
package com.thery.paymybuddy.controller;

import com.thery.paymybuddy.dto.AddRelationShipsRequest;
import com.thery.paymybuddy.dto.AddRelationShipsResponse;
import com.thery.paymybuddy.dto.RelationShipsDetailForTransferResponse;
import com.thery.paymybuddy.service.RelationShipsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.thery.paymybuddy.exception.RelationShipsServiceException.AddRelationShipsException;
import static com.thery.paymybuddy.exception.RelationShipsServiceException.RelationShipsDetailForTransferException;

/**
 * REST controller for managing relationships between clients in the dashboard.
 */
@RestController
@RequestMapping("/api/fr/client/dashboard")
public class RelationShipsController {

    private static final Logger logger = LogManager.getLogger(RelationShipsController.class);

    @Autowired
    RelationShipsService relationShipsService;


    /**
     * Endpoint to add a new relationship.
     *
     * @param addRelationShips DTO containing data for adding relationships
     * @return DTO indicating success or failure of adding relationships
     */
    @PostMapping("/relationships")
    @ResponseStatus(HttpStatus.CREATED)
    public AddRelationShipsResponse addRelationShips(@RequestBody AddRelationShipsRequest addRelationShips) throws AddRelationShipsException {
        logger.info("Received request to add new relationship.");
        AddRelationShipsResponse addRelationShipsResponse = relationShipsService.addRelationShips(addRelationShips);
        logger.info("Relationship added successfully.");
        return addRelationShipsResponse;
    }

    /**
     * Endpoint to retrieve general relationship details for transfer.
     *
     * @return DTO containing general relationship details for transfer
     */
    @GetMapping("/relationships")
    @ResponseStatus(HttpStatus.OK)
    public RelationShipsDetailForTransferResponse relationShipsDetailForTransfer() throws RelationShipsDetailForTransferException {
        logger.info("Received request to retrieve relationship details for transfer.");
        RelationShipsDetailForTransferResponse relationShipsDetailForTransferResponse = relationShipsService.relationShipsDetailForTransfer();
        logger.info("Relationship details retrieved successfully.");
        return relationShipsDetailForTransferResponse;
    }
}

