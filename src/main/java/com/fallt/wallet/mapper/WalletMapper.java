package com.fallt.wallet.mapper;

import com.fallt.wallet.domain.dto.request.UpsertWalletRequest;
import com.fallt.wallet.domain.dto.response.WalletBalance;
import com.fallt.wallet.domain.entity.Wallet;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.factory.Mappers.getMapper;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    WalletMapper INSTANCE = getMapper(WalletMapper.class);

    @Mapping(target = "balance", expression = "java(wallet.getAmount().toPlainString())")
    WalletBalance toResponse(Wallet wallet);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpsertWalletRequest request, @MappingTarget Wallet wallet);

}
