package com.thery.paymybuddy.Services;

import com.thery.paymybuddy.dto.ProfileClientChangeRequest;
import com.thery.paymybuddy.dto.ProfileClientChangeResponse;
import com.thery.paymybuddy.dto.ProfileClientResponse;
import com.thery.paymybuddy.dto.SavingClientResponse;
import com.thery.paymybuddy.models.Client;
import com.thery.paymybuddy.repository.ClientRepository;
import com.thery.paymybuddy.utils.InformationOnContextUtils;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.thery.paymybuddy.Exceptions.ClientServiceException.*;
import static com.thery.paymybuddy.constants.MessagesServicesConstants.*;

/**
 * Service class for handling client operations.
 */
@Service
public class ClientService {

    private static final Logger logger = LogManager.getLogger(ClientService.class);

    private final ClientRepository clientRepository;

    private final PasswordEncoder clientPasswordEncoder;
    /**
     * Constructor for ClientService.
     *
     * @param clientRepository the client repository
     * @param clientPasswordEncoder Encoder for client passwords.
     */
    public ClientService(ClientRepository clientRepository, PasswordEncoder clientPasswordEncoder) {
        this.clientRepository = clientRepository;
        this.clientPasswordEncoder = clientPasswordEncoder;
    }

    /**
     * Retrieves the profile of the client.
     *
     * @return  the profile client DTO
     * @throws GetProfileException if an error occurs while getting the profile
     */
    @Transactional
    public ProfileClientResponse getProfile() throws GetProfileException {
        logger.info("Attempting to retrieve client profile.");
        try {
            long clientId = Long.parseLong(InformationOnContextUtils.getIdClientFromContext());
            Client client = findById(clientId);
            return new ProfileClientResponse(client.getUsername(),client.getEmail());
        } catch (Exception e) {
            logger.error("Error while retrieving client profile: {}", e.getMessage());
            throw new GetProfileException(e);
        }
    }

    /**
     * Changes the profile of the client.
     *
     * @param profileClientChangeRequest the profile modification of client DTO
     * @return the profile client change success DTO
     * @throws ChangeProfileException if an error occurs while changing the profile
     */
    @Transactional
    public ProfileClientChangeResponse changeProfile(ProfileClientChangeRequest profileClientChangeRequest) throws ChangeProfileException {
        logger.info("Attempting to change client profile.");
        try {
            long clientId = Long.parseLong(InformationOnContextUtils.getIdClientFromContext());
            Client client = findById(clientId);
            if(!profileClientChangeRequest.getUsername().isEmpty()){
                client.setUsername(profileClientChangeRequest.getUsername());
            }
            if(!profileClientChangeRequest.getEmail().isEmpty()){
                client.setEmail(profileClientChangeRequest.getEmail());
            }
            if(!profileClientChangeRequest.getPassword().isEmpty()){
                client.setPassword(clientPasswordEncoder.encode(profileClientChangeRequest.getPassword()));
            }
            clientRepository.save(client);
            return new ProfileClientChangeResponse(CHANGE_PROFILE_SUCCESS);
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
            long clientId = Long.parseLong(InformationOnContextUtils.getIdClientFromContext());
            Client client = findById(clientId);
            return new SavingClientResponse(client.getSaving());
        } catch (Exception e) {
            logger.error("Error while retrieving saving client details: {}", e.getMessage());
            throw new GetSavingClientException(e);
        }
    }
    /**
     * Retrieves the client details about email.
     *
     * @return the client
     * @throws FindByEmailException if an error occurs while getting the client details
     */
    public Client findByEmail(String email) throws FindByEmailException {
        try {
            Client client = clientRepository.findByEmail(email);
            if (client == null) {
                throw new ClientNotFoundException();
            }
            return client;
        } catch (Exception e) {
            throw new FindByEmailException(e);
        }
    }
    /**
     * BackUp client details.
     *
     * @return the saving client DTO
     * @throws SaveClientException if an error occurs while backup client details
     */
    public Client saveClient(Client client) throws SaveClientException {
        try {
            return clientRepository.save(client);
        } catch (Exception e) {
            throw new SaveClientException(e);
        }
    }
    /**
     * retrieve  boolean about client exist.
     *
     * @return code{"true"} if client exist otherwise code{"false"}
     * @throws IsExistClientException if an error occurs while backup client details
     */
    public boolean isExistClient(String email) throws IsExistClientException {
        try {
            return clientRepository.existsByEmail(email);
        } catch (Exception e) {
            throw new IsExistClientException(e);
        }
    }

    /**
     * retrieve client detail when given id.
     *
     * @return client detail
     * @throws FindByIdException if there is no client to return
     */
    public Client findById(Long clientId) throws FindByIdException {
        try {
            Optional<Client> client = clientRepository.findById(clientId);
            if (client.isEmpty()){
                throw new AuthenticatedClientNotFoundException();
            }
            return client.get();
        } catch (Exception e) {
            throw new FindByIdException(e);
        }

    }
}
