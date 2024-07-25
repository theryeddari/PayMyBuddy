package com.thery.paymybuddy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thery.paymybuddy.configs.security.JwtClientServiceConfig;
import com.thery.paymybuddy.dto.ProfileClientResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;
import java.util.Set;

import static com.thery.paymybuddy.Exceptions.JwtClientServiceConfigException.*;
import static com.thery.paymybuddy.constants.MessageExceptionConstants.GET_PROFILE_EXCEPTION;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc()
public class ClientControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    JwtClientServiceConfig jwtClientServiceConfig;

    private ObjectMapper objectMapper;

    String jwtTokenAlice;

    String jwtTokenUnknownAuthenticated;


    @BeforeEach
    public void setUp() throws GenerateTokenConfigExceptionClient {
        objectMapper = new ObjectMapper();

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("1");
        Collection<? extends GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
        doReturn(authorities).when(authentication).getAuthorities();
        jwtTokenAlice = jwtClientServiceConfig.generateToken(authentication);
        when(authentication.getName()).thenReturn("10");
        jwtTokenUnknownAuthenticated = jwtClientServiceConfig.generateToken(authentication);

    }

    @Test
    void testGetProfile_Success() throws Exception {
        ProfileClientResponse profileClientResponse = new ProfileClientResponse("alice","alice@example.com");

        mockMvc.perform(get("/api/fr/client/dashboard/profil")
                .header("Authorization", "Bearer " + jwtTokenAlice))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(profileClientResponse)));
    }

    @Test
    void testGetProfile_() throws Exception {

        mockMvc.perform(get("/api/fr/client/dashboard/profil")
                        .header("Authorization", "Bearer " + jwtTokenUnknownAuthenticated))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(GET_PROFILE_EXCEPTION));
    }
}
