package com.desapp.crypto_exchange.DTOs

import com.desapp.crypto_exchange.model.CryptoActive
import com.desapp.crypto_exchange.model.OperationType
import com.desapp.crypto_exchange.model.TradeStatus
import java.time.LocalDateTime

data class TradeDTO(
    val id: Long?,
    val buyer: UserDTO,
    val seller: UserDTO,
    val cryptoActive: CryptoActive?,
    val nominalAmount: Float?,
    val tradePrice: Float?,
    val amountInArs: Float?,
    val createdDate: LocalDateTime?,
    val tradeStatus: TradeStatus?,
    val tradeType: OperationType?
)
