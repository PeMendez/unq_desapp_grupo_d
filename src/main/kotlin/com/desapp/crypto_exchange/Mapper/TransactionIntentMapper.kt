package com.desapp.crypto_exchange.Mapper

import com.desapp.crypto_exchange.DTOs.ResponseTransactionIntentDTO
import com.desapp.crypto_exchange.DTOs.TransactionIntentDTO
import com.desapp.crypto_exchange.model.TransactionIntent

class TransactionIntentMapper {

    companion object {

        fun toDTO(transactionIntent: TransactionIntent): ResponseTransactionIntentDTO {
            return ResponseTransactionIntentDTO(
                id = transactionIntent.id,
                ownerId = transactionIntent.owner?.id,
                priceInArs = transactionIntent.priceInArs,
                amount = transactionIntent.amount,
                operationType = transactionIntent.operationType,
                status = transactionIntent.status,
                createdDate = transactionIntent.createdDate
            )
        }

        fun toModel(transactionIntentDTO: TransactionIntentDTO): TransactionIntent {
            val transactionIntent = TransactionIntent()
            transactionIntent.amount = transactionIntentDTO.amount
            transactionIntent.operationType = transactionIntentDTO.operationType
            return transactionIntent
        }
    }
}
