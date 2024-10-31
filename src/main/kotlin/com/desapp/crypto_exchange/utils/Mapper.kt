package com.desapp.crypto_exchange.utils

import com.desapp.crypto_exchange.DTOs.ResponseTransactionIntentDTO
import com.desapp.crypto_exchange.DTOs.TransactionIntentDTO
import com.desapp.crypto_exchange.model.TransactionIntent

object Mapper {
    fun transactionIntentToResponseTransactionIntentDTO(transactionIntent: TransactionIntent) : ResponseTransactionIntentDTO {
        val DTO = ResponseTransactionIntentDTO()
        return DTO
    }

    fun dtoToTransactionIntent(transactionIntentDTO: TransactionIntentDTO):TransactionIntent {
        val transactionIntent = TransactionIntent()
        return transactionIntent
    }
}