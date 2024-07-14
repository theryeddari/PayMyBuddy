package com.thery.paymybuddy.controller;

import com.thery.paymybuddy.Services.ClientService;
import com.thery.paymybuddy.dto.ProfileClientChangeResponse;
import com.thery.paymybuddy.dto.ProfileClientChangeRequest;
import com.thery.paymybuddy.dto.ProfileClientResponse;
import com.thery.paymybuddy.dto.SavingClientResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.thery.paymybuddy.Exceptions.ClientServiceException.*;

/**
 * Controller class for managing client dashboard APIs.
 */
@RestController
@RequestMapping("/api/fr/client/dashboard")
public class ClientController {

    private static final Logger logger = LogManager.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    /**
     * GET endpoint to fetch client's profile.
     *
     * @return ProfileClientChangeRequest object representing the client's profile.
     * @throws GetProfileException if an error occurs while fetching the profile.
     */
    @GetMapping("/profil")
    @ResponseStatus(HttpStatus.OK)
    public ProfileClientResponse getProfile() throws GetProfileException {
        logger.info("Fetching client profile");
        ProfileClientResponse profileClientResponse = clientService.getProfile();
        logger.info("Client profile fetched successfully");
        return profileClientResponse;
    }

    /**
     * POST endpoint to update client's profile.
     *
     * @param ProfileClientChangeRequest ProfileClientChangeRequest object containing updated profile information.
     * @return ProfileClientChangeResponse object indicating success of profile update.
     * @throws ChangeProfileException if an error occurs while changing the profile.
     */
    @PostMapping("/profil")
    @ResponseStatus(HttpStatus.OK)
    public ProfileClientChangeResponse changeProfile(@RequestBody ProfileClientChangeRequest ProfileClientChangeRequest) throws ChangeProfileException {
        logger.info("Changing client profile with data: {}", ProfileClientChangeRequest);
        ProfileClientChangeResponse profileClientChangeResponse = clientService.changeProfile(ProfileClientChangeRequest);
        logger.info("Client profile changed successfully");
        return profileClientChangeResponse;
    }

    /**
     * GET endpoint to fetch client's saving profile.
     *
     * @return SavingClientResponse object representing the client's saving profile.
     * @throws GetSavingClientException if an error occurs while fetching the saving profile.
     */
    @GetMapping("/profil/saving")
    @ResponseStatus(HttpStatus.OK)
    public SavingClientResponse getSavingClient() throws GetSavingClientException {
        logger.info("Fetching client saving profile");
        SavingClientResponse SavingClientResponse = clientService.getSavingClient();
        logger.info("Client saving profile fetched successfully");
        return SavingClientResponse;
    }

}
