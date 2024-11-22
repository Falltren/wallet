package com.fallt.wallet.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpsertWalletResponse {

    @JsonProperty("is_success")
    private boolean isSuccess;

    @Builder.Default
    private LocalDateTime date = LocalDateTime.now();

}
