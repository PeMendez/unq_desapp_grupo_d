package com.desapp.crypto_exchange.DTOs

import com.desapp.crypto_exchange.model.CryptoActive
import com.desapp.crypto_exchange.model.OperationType

data class TransactionIntentDTO(
    val price: Float,
    val cryptoActive: CryptoActive,
    val amount: Float,
    val operationType: OperationType,

)
