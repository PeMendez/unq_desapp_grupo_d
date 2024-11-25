package com.desapp.crypto_exchange.Mapper

import com.desapp.crypto_exchange.DTOs.TradeDTO
import com.desapp.crypto_exchange.model.Trade

class TradeMapper {
    companion object {
        fun toDTO(trade: Trade): TradeDTO {
            return TradeDTO(
                id = trade.id,
                buyer = UserMapper.toDTO(trade.buyer!!),
                seller = UserMapper.toDTO(trade.seller!!),
                cryptoActive = trade.cryptoActive,
                nominalAmount = trade.nominalAmount,
                tradePrice = trade.tradePrice,
                amountInArs = trade.amountInArs,
                createdDate = trade.createdDate,
                tradeStatus = trade.tradeStatus,
                tradeType = trade.tradeType
            )
        }
    }
}
