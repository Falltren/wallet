package com.fallt.wallet.service;

import com.fallt.wallet.domain.dto.request.TransactionRequest;
import com.fallt.wallet.domain.dto.response.TransactionResponse;
import com.fallt.wallet.domain.dto.response.WalletBalance;

public interface WalletService {

    TransactionResponse changeAmount(TransactionRequest request);

    WalletBalance getBalance(String uuid);

}
