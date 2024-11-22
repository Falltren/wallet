package com.fallt.wallet.domain.dto.request;

import com.fallt.wallet.domain.entity.enums.OperationType;
import com.fallt.wallet.validation.OperationTypeValidation;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpsertWalletRequest {

    private String walletId;

    @OperationTypeValidation(enumClass = OperationType.class)
    private String operationType;

    @Positive(message = "amount should be positive")
    private BigDecimal amount;

}
