package com.thery.paymybuddy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thery.paymybuddy.configs.security.JwtClientServiceConfig;
import com.thery.paymybuddy.dto.*;
import com.thery.paymybuddy.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.thery.paymybuddy.Exceptions.JwtClientServiceConfigException.GenerateTokenConfigExceptionClient;
import static com.thery.paymybuddy.constants.MessageExceptionConstants.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc()
public class TransactionControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    JwtClientServiceConfig jwtClientServiceConfig;

    @SpyBean
    TransactionRepository transactionRepository;

    private ObjectMapper objectMapper;

    String jwtTokenBob;

    String jwtTokenUnknownAuthenticated;


    @BeforeEach
    public void setUp() throws GenerateTokenConfigExceptionClient {
        objectMapper = new ObjectMapper();

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("2");
        Collection<? extends GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("ROLE_CLIENT"));
        doReturn(authorities).when(authentication).getAuthorities();
        jwtTokenBob = jwtClientServiceConfig.generateToken(authentication);
        when(authentication.getName()).thenReturn("10");
        jwtTokenUnknownAuthenticated = jwtClientServiceConfig.generateToken(authentication);

    }

    @Test
    public void testGetGeneralTransferDetail_Success() throws Exception {
        TransferredGeneralDetailDTO transferredGeneralDetailDTO1 = new TransferredGeneralDetailDTO("alice@example.com", "Payment for services", 20.00);
        TransferredGeneralDetailDTO transferredGeneralDetailDTO2 = new TransferredGeneralDetailDTO("dave@example.com", "Birthday", 30.00);

        List<TransferredGeneralDetailDTO> transferredGeneralDetailDTOList = List.of(transferredGeneralDetailDTO1, transferredGeneralDetailDTO2);
        TransferredGeneralDetailResponse transferredGeneralDetailResponse = new TransferredGeneralDetailResponse(transferredGeneralDetailDTOList);

        mockMvc.perform(get("/api/fr/client/dashboard/transaction")
                        .header("Authorization", "Bearer " + jwtTokenBob))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(transferredGeneralDetailResponse)));
    }
    @Test
    public void testGetGeneralTransferDetail_Failed() throws Exception {
        when(transactionRepository.findBySender_Id(anyLong())).thenThrow(new RuntimeException("Test Exception response"));

        mockMvc.perform(get("/api/fr/client/dashboard/transaction")
                        .header("Authorization", "Bearer " + jwtTokenBob))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(GET_GENERAL_TRANSFER_DETAIL_EXCEPTION));
    }

}
