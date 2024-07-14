package com.thery.paymybuddy.Services;

import com.thery.paymybuddy.dto.ProfileClientChangeRequest;
import com.thery.paymybuddy.dto.ProfileClientChangeResponse;
import com.thery.paymybuddy.dto.ProfileClientResponse;
import com.thery.paymybuddy.dto.SavingClientResponse;
import com.thery.paymybuddy.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import static com.thery.paymybuddy.Exceptions.ClientServiceException.*;

/**
 * Service class for handling client operations.
 */
@Service
public class ClientService {

    private static final Logger logger = LogManager.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    /**
     * Constructor for ClientService.
     *
     * @param clientRepository the client repository
     */
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Retrieves the profile of the client.
     *
     * @return the profile client DTO
     * @throws GetProfileException if an error occurs while getting the profile
     */
    @Transactional
    public ProfileClientResponse getProfile() throws GetProfileException {
        logger.info("Attempting to retrieve client profile.");
        try {
            return new ProfileClientResponse();
        } catch (Exception e) {
            logger.error("Error while retrieving client profile: {}", e.getMessage());
            throw new GetProfileException(e);
        }
    }

    /**
     * Changes the profile of the client.
     *
     * @param profileClientChangeRequest the profile client DTO
     * @return the profile client change success DTO
     * @throws ChangeProfileException if an error occurs while changing the profile
     */
    @Transactional
    public ProfileClientChangeResponse changeProfile(ProfileClientChangeRequest profileClientChangeRequest) throws ChangeProfileException {
        logger.info("Attempting to change client profile.");
        try {
            return new ProfileClientChangeResponse();
        } catch (Exception e) {
            logger.error("Error while changing client profile: {}", e.getMessage());
            throw new ChangeProfileException(e);
        }
    }

    /**
     * Retrieves the saving client details.
     *
     * @return the saving client DTO
     * @throws GetSavingClientException if an error occurs while getting the saving client details
     */
    @Transactional
    public SavingClientResponse getSavingClient() throws GetSavingClientException {
        logger.info("Attempting to retrieve saving client details.");
        try {
            return new SavingClientResponse();
        } catch (Exception e) {
            logger.error("Error while retrieving saving client details: {}", e.getMessage());
            throw new GetSavingClientException(e);
        }
    }
}