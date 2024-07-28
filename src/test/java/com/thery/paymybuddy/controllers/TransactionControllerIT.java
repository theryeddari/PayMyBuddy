package com.thery.paymybuddy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thery.paymybuddy.configs.security.JwtClientServiceConfig;
import com.thery.paymybuddy.constants.MessagesServicesConstants;
import com.thery.paymybuddy.dto.*;
import com.thery.paymybuddy.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void testGetTransferredGeneralDetail_Success() throws Exception {
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
    public void testGetTransferredGeneralDetail_Failed() throws Exception {
        when(transactionRepository.findBySender_Id(anyLong())).thenThrow(new RuntimeException("Test Exception response"));

        mockMvc.perform(get("/api/fr/client/dashboard/transaction")
                        .header("Authorization", "Bearer " + jwtTokenBob))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(GET_GENERAL_TRANSFER_DETAIL_EXCEPTION));
    }

    @Test
    public void testDoTransfer_Success() throws Exception {
        DoTransferRequest doTransferRequest = new DoTransferRequest("alice@example.com", "description test", 10.0);
        DoTransferResponse doTransferResponseExcepted = new DoTransferResponse(MessagesServicesConstants.TRANSFER_SUCCESS);

        mockMvc.perform(post("/api/fr/client/dashboard/transfer")
                        .header("Authorization", "Bearer " + jwtTokenBob)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doTransferRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(doTransferResponseExcepted)));
    }

    //not enough funds for the transfer
    @Test
    public void testDoTransfer_isFundAvailableException() throws Exception {
        DoTransferRequest doTransferRequest = new DoTransferRequest("alice@example.com", "description test", 1000.0);

        mockMvc.perform(post("/api/fr/client/dashboard/transfer")
                        .header("Authorization", "Bearer " + jwtTokenBob)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doTransferRequest)))
                .andExpect(status().isPaymentRequired())
                .andExpect(content().string(DO_TRANSFER_EXCEPTION + MORE_INFO + IS_FUND_AVAILABLE_EXCEPTION));
    }
    //try to transfer fund with somebody who is not in its relation
    @Test
    public void testDoTransfer_IsTransactionBetweenFriendException() throws Exception {
        DoTransferRequest doTransferRequest = new DoTransferRequest("carol@example.com", "description test", 10.0);

        mockMvc.perform(post("/api/fr/client/dashboard/transfer")
                        .header("Authorization", "Bearer " + jwtTokenBob)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(doTransferRequest)))
                .andExpect(status().isForbidden())
                .andExpect(content().string(DO_TRANSFER_EXCEPTION + MORE_INFO + IS_TRANSACTION_BETWEEN_FRIEND_EXCEPTION));
    }
}