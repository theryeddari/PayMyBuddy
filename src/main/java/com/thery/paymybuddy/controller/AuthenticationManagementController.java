package com.thery.paymybuddy.controller;

import com.thery.paymybuddy.dto.*;
import com.thery.paymybuddy.service.AuthenticationManagementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.thery.paymybuddy.exception.AuthenticationManagementServiceException.*;

/**
 * Controller class for managing authentication-related operations.
 * This class handles sign-up, sign-in, and logout requests.
 */
@RestController
@RequestMapping("api/fr/auth")
public class AuthenticationManagementController {

    private static final Logger logger = LogManager.getLogger(AuthenticationManagementController.class);

    @Autowired
    private AuthenticationManagementService authenticationManagementService;

    /**
     * Signs up a new client.
     *
     * @param newClient the DTO containing the new client's information
     * @return a DTO confirming the successful sign-up
     * @throws SignUpClientException if there is an error during client sign-up
     */
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpResponse signUpClient(@RequestBody SignUpRequest newClient) throws SignUpClientException {
        logger.info("Received request to sign up a new client");
        SignUpResponse response = authenticationManagementService.signUpClient(newClient);
        logger.info("Client signed up successfully");
        return response;
    }

    /**
     * Signs in an existing client.
     *
     * @param alreadyExistClient the DTO containing the existing client's credentials
     * @return a DTO confirming the successful sign-in
     */
    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signInClient(@RequestBody SignInRequest alreadyExistClient) throws SignInClientException {
        logger.info("Received request to sign in an existing client");
        SignInResponseDTO response = authenticationManagementService.signInClient(alreadyExistClient);
        logger.info("Client signed in successfully");
        return ResponseEntity.ok().headers(jwtHeader -> jwtHeader.add("Authorization", "Bearer " + response.getJwtToken())).body(response.getSignInResponse());
    }

    /**
     * Logs out the current client.
     *
     * @return a DTO confirming the successful logout
     */
    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public LogOutResponse logOutClient() throws LogOutClientException {
        logger.info("Received request to log out client");
        LogOutResponse response = authenticationManagementService.logOutClient();
        logger.info("Client logged out successfully");
        return response;
    }
}