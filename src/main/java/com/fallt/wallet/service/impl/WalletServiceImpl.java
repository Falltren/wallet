package com.fallt.wallet.service.impl;

import com.fallt.wallet.domain.dto.request.UpsertWalletRequest;
import com.fallt.wallet.domain.dto.response.UpsertWalletResponse;
import com.fallt.wallet.domain.dto.response.WalletBalance;
import com.fallt.wallet.domain.entity.Wallet;
import com.fallt.wallet.domain.entity.enums.OperationType;
import com.fallt.wallet.exception.EntityNotFoundException;
import com.fallt.wallet.exception.NotHaveEnoughFunds;
import com.fallt.wallet.mapper.WalletMapper;
import com.fallt.wallet.repository.WalletRepository;
import com.fallt.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public UpsertWalletResponse changeAmount(UpsertWalletRequest request) {
        Wallet wallet = getWalletById(request.getWalletId());
        BigDecimal newValue = null;
        if (request.getOperationType().equals(OperationType.DEPOSIT.name())) {
            newValue = wallet.getAmount().add(request.getAmount());
        } else if (request.getOperationType().equals(OperationType.WITHDRAW.name())) {
            newValue = wallet.getAmount().subtract(request.getAmount());
            if (newValue.compareTo(BigDecimal.ZERO) < 0) {
                throw new NotHaveEnoughFunds("You don`t have enough money for the transaction");
            }
        }
        wallet.setAmount(newValue);
        walletRepository.save(wallet);
        return UpsertWalletResponse.builder()
                .isSuccess(true)
                .build();
    }

    @Override
    public WalletBalance getBalance(String uuid) {
        Wallet wallet = getWalletById(uuid);
        return WalletMapper.INSTANCE.toResponse(wallet);
    }

    private Wallet getWalletById(String uuid) {
        UUID id = UUID.fromString(uuid);
        Optional<Wallet> wallet = walletRepository.getWalletById(id);
        if (wallet.isEmpty()) {
            throw new EntityNotFoundException(MessageFormat.format("Wallet with ID: {0} not found", uuid));
        }
        return wallet.get();
    }

}
