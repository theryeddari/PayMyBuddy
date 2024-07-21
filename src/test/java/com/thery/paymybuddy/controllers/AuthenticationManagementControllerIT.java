package com.thery.paymybuddy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thery.paymybuddy.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.thery.paymybuddy.constants.MessageExceptionConstants.CLIENT_ALREADY_EXISTS_EXCEPTION;
import static com.thery.paymybuddy.constants.MessageExceptionConstants.CLIENT_NOT_FOUND_EXCEPTION;
import static com.thery.paymybuddy.constants.MessagesServicesConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc()
public class AuthenticationManagementControllerIT {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testSignUpClient_Success() throws Exception {
        SignUpDTO signUpDTO = new SignUpDTO("test","test@example.com", "testPassword");
        SignedUpConfirmDTO signedUpConfirmDTO = new SignedUpConfirmDTO("test","test@example.com");

        mockMvc.perform(post("/api/fr/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(signedUpConfirmDTO)));
    }

    @Test
    public void testSignUpClient_ClientAlreadyExistException() throws Exception {
        SignUpDTO signUpDTO = new SignUpDTO("alice","alice@example.com", "alicep");

        mockMvc.perform(post("/api/fr/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpDTO)))
                .andExpect(status().isConflict())
                .andExpect(content().string(CLIENT_ALREADY_EXISTS_EXCEPTION));
    }

    @Test
    public void testSignInClient_Success() throws Exception {
        SignInDTO signInDTO = new SignInDTO("alice@example.com", "alicep");
        SignInSuccessDTO  signInSuccessDTO = new SignInSuccessDTO(SIGN_IN_SUCCESS);
        mockMvc.perform(post("/api/fr/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInDTO)))
                .andExpect(status().isOk())
                .andExpect(header -> {
                        Optional<String> authorizationHeader = Optional.ofNullable(header.getResponse().getHeader(HttpHeaders.AUTHORIZATION));
                        if (authorizationHeader.isEmpty() || !authorizationHeader.get().startsWith("Bearer ")) {
                                throw new AssertionError("AUTHORIZATION is not present check jwt service" );
                         }
                })
                .andExpect(content().json(objectMapper.writeValueAsString(signInSuccessDTO)));
    }

    @Test
    public void testSignInClient_ClientNotFoundException() throws Exception {
        SignInDTO signInDTO = new SignInDTO("noclient@exemple.com", "noclientp");
        mockMvc.perform(post("/api/fr/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(CLIENT_NOT_FOUND_EXCEPTION + signInDTO.getEmail()));
    }

    @Test
    public void testLogOutClient() throws Exception {
        LogOutSuccessDTO logOutSuccessDTO = new LogOutSuccessDTO(LOG_OUT_SUCCESS);
        mockMvc.perform(get("/api/fr/auth/logout"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(logOutSuccessDTO)));
    }

}
