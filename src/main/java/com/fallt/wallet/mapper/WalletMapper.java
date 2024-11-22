package com.fallt.wallet.mapper;

import com.fallt.wallet.domain.dto.response.WalletBalance;
import com.fallt.wallet.domain.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    WalletMapper INSTANCE = getMapper(WalletMapper.class);

    @Mapping(target = "balance", expression = "java(wallet.getAmount().toPlainString())")
    WalletBalance toResponse(Wallet wallet);

}
