package com.desapp.crypto_exchange.DTOs

import com.desapp.crypto_exchange.model.OperationType
import com.desapp.crypto_exchange.model.TransactionStatus
import java.time.LocalDateTime

data class ResponseTransactionIntentDTO(
    val id: Long?,
    val ownerId: Long?,
    val price: Float?,
    val priceInArs: Float?,
    val amount: Float?,
    val operationType: OperationType?,
    val status: TransactionStatus?,
    val createdDate: LocalDateTime?
)
