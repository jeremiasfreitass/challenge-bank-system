package com.challange.bank.system.controller;

import com.challange.bank.system.dto.TransactionDTO;
import com.challange.bank.system.dto.TransactionRequestDTO;
import com.challange.bank.system.exception.BusinessException;
import com.challange.bank.system.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    private static final String URI = "/bank/transfer";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private TransactionService transactionService;

    @Test
    void createTransaction_shouldReturnOk_whenRequestIsValid() throws Exception {
        TransactionRequestDTO request = new TransactionRequestDTO(new BigDecimal("100.00"), 1L, 2L);
        TransactionDTO serviceResponse = new TransactionDTO("Transação realizada com sucesso.");

        when(transactionService.createTransaction(request)).thenReturn(serviceResponse);

        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Transação realizada com sucesso."));
    }

    @Test
    void createTransaction_shouldReturnBadRequest_whenRequestIsInvalid() throws Exception {
        TransactionRequestDTO request = new TransactionRequestDTO(null, null, null); // Invalid request

        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsInAnyOrder(
                        "Payee não pode ser vazio",
                        "Payer não pode ser vazio",
                        "Value não pode ser vazio"
                )));
    }

    @Test
    void createTransaction_shouldReturnUnprocessableEntity_whenServiceThrowsBusinessException() throws Exception {
        TransactionRequestDTO request = new TransactionRequestDTO(new BigDecimal("100.00"), 1L, 2L);
        String errorMessage = "Transação inválida: saldo insuficiente.";

        when(transactionService.createTransaction(request))
                .thenThrow(new BusinessException(errorMessage));

        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(errorMessage));
    }
}