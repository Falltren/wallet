package com.fallt.wallet.service;

import com.fallt.wallet.domain.dto.request.UpsertWalletRequest;
import com.fallt.wallet.domain.dto.response.UpsertWalletResponse;
import com.fallt.wallet.domain.dto.response.WalletBalance;

public interface WalletService {

    UpsertWalletResponse changeAmount(UpsertWalletRequest request);

    WalletBalance getBalance(String uuid);

}
