package com.thery.paymybuddy.controller;

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

import static com.thery.paymybuddy.constant.MessageExceptionConstants.CLIENT_ALREADY_EXISTS_EXCEPTION;
import static com.thery.paymybuddy.constant.MessageExceptionConstants.CLIENT_NOT_FOUND_EXCEPTION;
import static com.thery.paymybuddy.constant.MessagesServicesConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        SignUpRequest signUpRequest = new SignUpRequest("test", "test@example.com", "testPassword");
        SignUpResponse signUpResponse = new SignUpResponse(SIGN_UP_SUCCESS);

        mockMvc.perform(post("/api/fr/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(signUpResponse)));
    }

    @Test
    public void testSignUpClient_ClientAlreadyExistException() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("alice", "alice@example.com", "alicep");

        mockMvc.perform(post("/api/fr/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isConflict())
                .andExpect(content().string(CLIENT_ALREADY_EXISTS_EXCEPTION));
    }

    @Test
    public void testSignInClient_Success() throws Exception {
        SignInRequest signInRequest = new SignInRequest("alice@example.com", "alicep");
        SignInResponse signInSuccessDTO = new SignInResponse(SIGN_IN_SUCCESS);
        mockMvc.perform(post("/api/fr/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isOk())
                .andExpect(header -> {
                    Optional<String> authorizationHeader = Optional.ofNullable(header.getResponse().getHeader(HttpHeaders.AUTHORIZATION));
                    if (authorizationHeader.isEmpty() || !authorizationHeader.get().startsWith("Bearer ")) {
                        throw new AssertionError("AUTHORIZATION is not present check jwt service");
                    }
                })
                .andExpect(content().json(objectMapper.writeValueAsString(signInSuccessDTO)));
    }

    @Test
    public void testSignInClient_ClientNotFoundException() throws Exception {
        SignInRequest signInRequest = new SignInRequest("noclient@exemple.com", "noclientp");
        mockMvc.perform(post("/api/fr/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInRequest)))
                .andExpect(status().isNotFound())
                .andExpect(content().string(CLIENT_NOT_FOUND_EXCEPTION));
    }

    @Test
    public void testLogOutClient() throws Exception {
        LogOutResponse logOutResponse = new LogOutResponse(LOG_OUT_SUCCESS);
        mockMvc.perform(get("/api/fr/auth/logout"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(logOutResponse)));
    }

}
