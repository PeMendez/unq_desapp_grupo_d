package com.desapp.crypto_exchange.Service.Impl

import com.desapp.crypto_exchange.Repository.TransactionIntentRepository
import com.desapp.crypto_exchange.Service.TransactionIntentService
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Transactional
class TransactionIntentServiceImpl : TransactionIntentService {
    @Autowired
    lateinit var transactionIntentRepository: TransactionIntentRepository
}