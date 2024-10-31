package com.desapp.crypto_exchange.Service

import com.desapp.crypto_exchange.model.TransactionIntent

interface TransactionIntentService {
    fun createTransactionIntent(transactionIntent: TransactionIntent, id: Long) : TransactionIntent
}