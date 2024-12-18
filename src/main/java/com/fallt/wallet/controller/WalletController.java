package com.fallt.wallet.controller;

import com.fallt.wallet.domain.dto.request.UpsertWalletRequest;
import com.fallt.wallet.domain.dto.response.UpsertWalletResponse;
import com.fallt.wallet.domain.dto.response.WalletBalance;
import com.fallt.wallet.service.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/api/v1/wallets/{WALLET_UUID}")
    public ResponseEntity<WalletBalance> getBalance(@PathVariable(value = "WALLET_UUID") String uuid) {
        return ResponseEntity.ok(walletService.getBalance(uuid));
    }

    @PostMapping("/api/v1/wallet")
    public ResponseEntity<UpsertWalletResponse> changeBalance(@RequestBody @Valid UpsertWalletRequest request) {
        return ResponseEntity.ok(walletService.changeBalance(request));
    }

}
