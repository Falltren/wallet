package com.fallt.wallet.controller;

import com.fallt.wallet.domain.dto.request.TransactionRequest;
import com.fallt.wallet.domain.dto.response.TransactionResponse;
import com.fallt.wallet.domain.dto.response.WalletBalance;
import com.fallt.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
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
    public WalletBalance getBalance(@PathVariable(value = "WALLET_UUID") String uuid) {
        return walletService.getBalance(uuid);
    }

    @PostMapping("/api/v1/wallet")
    public TransactionResponse changeBalance(@RequestBody TransactionRequest request) {
        return walletService.changeAmount(request);
    }

}