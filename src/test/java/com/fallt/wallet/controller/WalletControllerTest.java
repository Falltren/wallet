package com.fallt.wallet.controller;

import com.fallt.wallet.AbstractTest;
import com.fallt.wallet.domain.dto.request.UpsertWalletRequest;
import com.fallt.wallet.domain.dto.response.WalletBalance;
import com.fallt.wallet.repository.WalletRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.UUID;

import static com.fallt.wallet.TestConstant.CHANGE_BALANCE_PATH;
import static com.fallt.wallet.TestConstant.DEPOSIT_OPERATION;
import static com.fallt.wallet.TestConstant.FIRST_ID;
import static com.fallt.wallet.TestConstant.GET_BALANCE_PATH;
import static com.fallt.wallet.TestConstant.INVALID_ID;
import static com.fallt.wallet.TestConstant.SECOND_ID;
import static com.fallt.wallet.TestConstant.WITHDRAW_OPERATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "/sql/walletcontroller_before_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/walletcontroller_after_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class WalletControllerTest extends AbstractTest {

    @Autowired
    private WalletRepository walletRepository;

    @Test
    @DisplayName("Success deposit operation")
    void whenDepositMoney_thenReturnOk() throws Exception {
        UpsertWalletRequest request = UpsertWalletRequest.builder()
                .walletId(FIRST_ID)
                .operationType(DEPOSIT_OPERATION)
                .amount(BigDecimal.valueOf(500))
                .build();
        String content = objectMapper.writeValueAsString(request);
        BigDecimal expected = BigDecimal.valueOf(1500);

        mockMvc.perform(post(CHANGE_BALANCE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true));
        BigDecimal amount = walletRepository.getReferenceById(UUID.fromString(FIRST_ID)).getAmount();
        assertThat(amount).isEqualByComparingTo(expected);
    }

    @Test
    @DisplayName("Success withdraw operation")
    void whenWithdrawMoney_thenReturnOk() throws Exception {
        UpsertWalletRequest request = UpsertWalletRequest.builder()
                .walletId(SECOND_ID)
                .operationType(WITHDRAW_OPERATION)
                .amount(BigDecimal.valueOf(1000))
                .build();
        String content = objectMapper.writeValueAsString(request);
        BigDecimal expected = BigDecimal.valueOf(1000);

        mockMvc.perform(post(CHANGE_BALANCE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true));
        BigDecimal amount = walletRepository.getReferenceById(UUID.fromString(SECOND_ID)).getAmount();
        assertThat(amount).isEqualByComparingTo(expected);
    }

    @Test
    @DisplayName("Use incorrect operation type")
    void whenUsingIncorrectOperationType_thenReturnBadRequest() throws Exception {
        UpsertWalletRequest request = UpsertWalletRequest.builder()
                .walletId(FIRST_ID)
                .operationType("INCORRECT")
                .amount(BigDecimal.valueOf(1000))
                .build();
        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(post(CHANGE_BALANCE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Invalid wallet id")
    void whenUseInvalidId_thenReturnNotFound() throws Exception {
        UpsertWalletRequest request = UpsertWalletRequest.builder()
                .walletId(INVALID_ID)
                .operationType(DEPOSIT_OPERATION)
                .amount(BigDecimal.valueOf(1000))
                .build();
        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(post(CHANGE_BALANCE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Not enough money for withdraw")
    void whenWithdrawAndNotEnoughMoney_thenReturnBadRequest() throws Exception {
        UpsertWalletRequest request = UpsertWalletRequest.builder()
                .walletId(SECOND_ID)
                .operationType(WITHDRAW_OPERATION)
                .amount(BigDecimal.valueOf(5000))
                .build();
        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(post(CHANGE_BALANCE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Get wallet balance")
    void whenGetBalance_thenReturnBalance() throws Exception {
        WalletBalance expected = WalletBalance.builder()
                .balance("1000.00")
                .build();

        mockMvc.perform(get(GET_BALANCE_PATH, FIRST_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expected)));
    }

    @Test
    @DisplayName("Get balance by invalid id")
    void whenGetBalanceByInvalidId_thenReturnNotFound() throws Exception {
        mockMvc.perform(get(GET_BALANCE_PATH, INVALID_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
