package com.desapp.crypto_exchange.Mapper

import com.desapp.crypto_exchange.DTOs.ResponseTransactionIntentDTO
import com.desapp.crypto_exchange.DTOs.TransactionIntentDTO
import com.desapp.crypto_exchange.model.TransactionIntent

class TransactionIntentMapper {

    companion object {

        fun toDTO(transactionIntent: TransactionIntent): ResponseTransactionIntentDTO {
            val DTO = ResponseTransactionIntentDTO()
            return DTO
        }

        fun toModel(transactionIntentDTO: TransactionIntentDTO): TransactionIntent {
            val transactionIntent = TransactionIntent()
            return transactionIntent
        }
    }
}