package com.fallt.wallet.service.impl;

import com.fallt.wallet.domain.dto.request.UpsertWalletRequest;
import com.fallt.wallet.domain.entity.Transaction;
import com.fallt.wallet.domain.entity.Wallet;
import com.fallt.wallet.domain.entity.enums.OperationType;
import com.fallt.wallet.repository.TransactionRepository;
import com.fallt.wallet.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public void save(UpsertWalletRequest request, Wallet wallet) {
        Transaction transaction = createTransaction(request);
        transaction.setWallet(wallet);
        transactionRepository.save(transaction);
    }

    private Transaction createTransaction(UpsertWalletRequest request) {
        return Transaction.builder()
                .type(OperationType.valueOf(request.getOperationType()))
                .amount(request.getAmount())
                .build();
    }

}
