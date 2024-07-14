package com.thery.paymybuddy.Services;

import com.thery.paymybuddy.configs.security.JwtClientServiceConfig;
import com.thery.paymybuddy.dto.*;
import com.thery.paymybuddy.models.Client;
import com.thery.paymybuddy.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.thery.paymybuddy.Exceptions.AuthenticationManagementServiceException.*;
import static com.thery.paymybuddy.constants.MessagesServicesConstants.*;

/**
 * Service class for managing authentication operations such as sign up, sign in, and log out.
 */
@Service
public class AuthenticationManagementService {

    private static final Logger logger = LogManager.getLogger(AuthenticationManagementService.class);

    private final PasswordEncoder clientPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ClientRepository clientRepository;
    private final JwtClientServiceConfig jwtClientServiceConfig;

    /**
     * Constructs an instance of AuthenticationManagementService.
     *
     * @param clientRepository               Repository for accessing client data.
     * @param clientPasswordEncoder         Encoder for client passwords.
     * @param authenticationConfiguration   Authentication configuration manager.
     * @param jwtClientServiceConfig                     Service for handling JWT operations.
     * @throws Exception                     Throws exception if initialization fails.
     */
    public AuthenticationManagementService(ClientRepository clientRepository, @Qualifier("clientPasswordEncoder") PasswordEncoder clientPasswordEncoder, AuthenticationConfiguration authenticationConfiguration, JwtClientServiceConfig jwtClientServiceConfig) throws Exception {
        this.clientRepository = clientRepository;
        this.clientPasswordEncoder = clientPasswordEncoder;
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.jwtClientServiceConfig = jwtClientServiceConfig;
    }

    /**
     * Registers a new client.
     *
     * @param newClient     DTO containing new client details.
     * @return              Confirmation DTO with client details upon successful registration.
     * @throws SignUpClientException     Throws exception if client registration fails.
     */
    @Transactional
    public SignUpResponse signUpClient(SignUpRequest newClient) throws SignUpClientException {
        try {
            if (clientRepository.findByEmail(newClient.getEmail()) != null) {
                throw new ClientAlreadyExistException();
            }

            Client client = new Client();
            client.setUsername(newClient.getUsername());
            client.setEmail(newClient.getEmail());
            client.setPassword(clientPasswordEncoder.encode(newClient.getPassword()));
            client.setRole("CLIENT");
            client.setSaving(0);

            Client clientSaved = clientRepository.save(client);

            logger.info("Client signed up successfully: {}", clientSaved.getUsername());

            return new SignUpResponse(clientSaved.getUsername(), clientSaved.getEmail());

        } catch (Exception e) {
            logger.error("Error during client sign up", e);
            throw new SignUpClientException(e);
        }
    }

    /**
     * Authenticates a client.
     *
     * @param SignInRequest     DTO containing client's sign-in credentials.
     * @return              Success DTO with JWT token upon successful authentication.
     * @throws SignInClientException    Throws exception if client authentication fails.
     */
    @Transactional
    public SignInResponseDTO  signInClient(SignInRequest SignInRequest) throws SignInClientException {
        try {
            Optional<Client> client = Optional.ofNullable(clientRepository.findByEmail(SignInRequest.getEmail()));
            if (client.isEmpty()) {
                throw new RuntimeException("Client not found");
                //TODO: code and add ClientNotFoundException() in AuthenticationManagementServiceException.java;
            }
            Long clientId = client.get().getId();
            UsernamePasswordAuthenticationToken credential = new UsernamePasswordAuthenticationToken(clientId, SignInRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(credential);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwtToken = jwtClientServiceConfig.generateToken(authentication);

            logger.info("Client signed in successfully: {}", SignInRequest.getEmail());

            SignInResponse SignInResponse =  new SignInResponse(SIGN_IN_SUCCESS);
            return new SignInResponseDTO(jwtToken, SignInResponse);
        } catch (Exception e) {
            logger.error("Error during client sign in", e);
            throw new SignInClientException(e);
        }
    }

    /**
     * Logs out a client.
     *
     * @return              Success DTO upon successful client log out.
     * @throws LogOutClientException    Throws exception if client log out fails.
     */
    @Transactional
    public LogOutResponse logOutClient() throws LogOutClientException {
        try {
            logger.info("Client logged out successfully");
            return new LogOutResponse(LOG_OUT_SUCCESS);

        } catch (Exception e) {
            logger.error("Error during client log out", e);
            throw new LogOutClientException(e);
        }
    }

        /**
         * Retrieves the username (email) from the JWT token.
         *
         * @return the username as a string
         */

        public String getIdClientFromContext() throws GetIdClientFromContextException {
            try {
                String id = SecurityContextHolder.getContext().getAuthentication().getName();
                logger.debug("Retrieved id from Context: {}", id);
                return id;
            } catch (Exception e) {
                throw new GetIdClientFromContextException(e);
            }
    }
}
