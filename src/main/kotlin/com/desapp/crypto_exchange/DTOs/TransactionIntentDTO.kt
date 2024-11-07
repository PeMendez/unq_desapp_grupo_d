package com.desapp.crypto_exchange.DTOs

import com.desapp.crypto_exchange.model.OperationType

data class TransactionIntentDTO(
    val ownerId: Long,
    val priceId: Long,
    val amount: Float,
    val operationType: OperationType
)
