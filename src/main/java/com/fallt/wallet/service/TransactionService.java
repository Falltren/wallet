package com.fallt.wallet.service;

import com.fallt.wallet.domain.dto.request.UpsertWalletRequest;
import com.fallt.wallet.domain.entity.Wallet;

public interface TransactionService {

    void save(UpsertWalletRequest request, Wallet wallet);

}
