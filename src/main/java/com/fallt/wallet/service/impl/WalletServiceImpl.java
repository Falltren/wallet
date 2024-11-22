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
import com.fallt.wallet.service.TransactionService;
import com.fallt.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final TransactionService transactionService;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @CacheEvict(value = "wallets", key = "#request.walletId")
    public UpsertWalletResponse changeBalance(UpsertWalletRequest request) {
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
        Wallet savedWallet = walletRepository.save(wallet);
        transactionService.save(request, savedWallet);
        return UpsertWalletResponse.builder()
                .isSuccess(true)
                .build();
    }

    @Override
    @Cacheable(value = "wallets", key = "#uuid")
    public WalletBalance getBalance(String uuid) {
        Wallet wallet = getWalletById(uuid);
        log.info("Вызов метода getBalance()");
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
