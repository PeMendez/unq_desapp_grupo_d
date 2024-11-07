package com.desapp.crypto_exchange.Service.Impl

import com.desapp.crypto_exchange.Service.DolarService
import com.desapp.crypto_exchange.repository.TransactionIntentRepository
import com.desapp.crypto_exchange.Service.TransactionIntentService
import com.desapp.crypto_exchange.model.TransactionIntent
import com.desapp.crypto_exchange.repository.PriceRepository
import com.desapp.crypto_exchange.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
@Transactional
class TransactionIntentServiceImpl : TransactionIntentService {
    @Autowired
    lateinit var transactionIntentRepository: TransactionIntentRepository

    @Autowired
    lateinit var priceRepository: PriceRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var dolarService: DolarService

    override fun createTransactionIntent(transactionIntent: TransactionIntent, id: Long) : TransactionIntent {
        val user = userRepository.findById(id).orElseThrow {
            UsernameNotFoundException("User with id $id was not found")
        }
        val price = priceRepository.findFirstByCryptoActiveOrderByPriceDesc(transactionIntent.price!!.cryptoActive!!)
            ?: throw IllegalArgumentException("No price found for crypto active")

        transactionIntent.owner = user
        transactionIntent.price = price

        val purchaseDolarPriceInArs = dolarService.getDolarCryptoPrice()?.compra?.toFloat()
            ?: throw Exception("No se pudo obtener el precio del dólar")

        transactionIntent.priceInArs = transactionIntent.price!!.price!! * purchaseDolarPriceInArs

        transactionIntent.validatePrice(transactionIntent.price!!.price!!, price.price!!)
        transactionIntentRepository.save(transactionIntent)

        user.addTransactionIntent(transactionIntent)
        userRepository.save(user)

        return transactionIntent
    }
}