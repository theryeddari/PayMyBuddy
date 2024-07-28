package com.thery.paymybuddy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thery.paymybuddy.configs.security.JwtClientServiceConfig;
import com.thery.paymybuddy.dto.AddRelationShipsRequest;
import com.thery.paymybuddy.dto.AddRelationShipsResponse;
import com.thery.paymybuddy.dto.RelationShipsDetailForTransferResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.thery.paymybuddy.exception.JwtClientServiceConfigException.*;
import static com.thery.paymybuddy.constant.MessageExceptionConstants.RELATIONSHIPS_ALREADY_EXIST_EXCEPTION;
import static com.thery.paymybuddy.constant.MessageExceptionConstants.SELF_ORIENTED_RELATIONSHIP_EXCEPTION;
import static com.thery.paymybuddy.constant.MessagesServicesConstants.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc()
public class RelationShipsControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    JwtClientServiceConfig jwtClientServiceConfig;

    private ObjectMapper objectMapper;

    String jwtTokenBob;

    String jwtTokenAlice;


    @BeforeEach
    public void setUp() throws GenerateTokenConfigExceptionClient {
        objectMapper = new ObjectMapper();

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("2");
        Collection<? extends GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
        doReturn(authorities).when(authentication).getAuthorities();
        jwtTokenBob = jwtClientServiceConfig.generateToken(authentication);
        when(authentication.getName()).thenReturn("1");
        jwtTokenAlice = jwtClientServiceConfig.generateToken(authentication);

    }

    @Test
    public void testAddRelationShips_Success() throws Exception {
        AddRelationShipsRequest addRelationShipsRequest = new AddRelationShipsRequest("alice@example.com");
        AddRelationShipsResponse addRelationShipsResponse = new AddRelationShipsResponse(ADD_RELATION_SUCCESS);

        mockMvc.perform(post("/api/fr/client/dashboard/relationships")
                        .header("Authorization", "Bearer " + jwtTokenBob)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addRelationShipsRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(addRelationShipsResponse)));
    }
    @Test
    public void testAddRelationShips_RelationshipsAlreadyExistException() throws Exception {
        AddRelationShipsRequest addRelationShipsRequest = new AddRelationShipsRequest("bob@example.com");

        mockMvc.perform(post("/api/fr/client/dashboard/relationships")
                        .header("Authorization", "Bearer " + jwtTokenAlice)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRelationShipsRequest)))
                .andExpect(status().isConflict())
                .andExpect(content().string(RELATIONSHIPS_ALREADY_EXIST_EXCEPTION));
    }

    @Test
    public void testAddRelationShips_SelfOrientedRelationshipException() throws Exception {
        AddRelationShipsRequest addRelationShipsRequest = new AddRelationShipsRequest("bob@example.com");

        mockMvc.perform(post("/api/fr/client/dashboard/relationships")
                        .header("Authorization", "Bearer " + jwtTokenBob)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addRelationShipsRequest)))
                .andExpect(status().isConflict())
                .andExpect(content().string(SELF_ORIENTED_RELATIONSHIP_EXCEPTION));
    }

    @Test
    public void testRelationShipsDetailForTransfer_Success() throws Exception {
        RelationShipsDetailForTransferResponse relationShipsDetailForTransferResponse = new RelationShipsDetailForTransferResponse(List.of("bob@example.com","carol@example.com","dave@example.com"));

        mockMvc.perform(get("/api/fr/client/dashboard/relationships")
                        .header("Authorization", "Bearer " + jwtTokenAlice)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(relationShipsDetailForTransferResponse)));
    }
}
