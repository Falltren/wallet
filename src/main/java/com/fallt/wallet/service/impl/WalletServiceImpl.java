package com.fallt.wallet.service.impl;

import com.fallt.wallet.domain.dto.request.TransactionRequest;
import com.fallt.wallet.domain.dto.response.TransactionResponse;
import com.fallt.wallet.domain.dto.response.WalletBalance;
import com.fallt.wallet.repository.WalletRepository;
import com.fallt.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public TransactionResponse changeAmount(TransactionRequest request) {
        return null;
    }

    @Override
    public WalletBalance getBalance(String uuid) {
        return null;
    }

}
