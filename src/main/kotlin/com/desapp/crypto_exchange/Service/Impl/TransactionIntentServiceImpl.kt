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

        val lastPrice = priceRepository.findFirstByCryptoActiveOrderByPriceDesc(transactionIntent.price!!.cryptoActive!!)
        val purchaseDolarPriceInArs = dolarService.getDolarCryptoPrice()?.compra?.toFloat()
            ?: throw Exception("No se pudo obtener el precio del dólar")

        val calculatedPriceInArs = transactionIntent.price!!.price!! * purchaseDolarPriceInArs
        transactionIntent.priceInArs = calculatedPriceInArs

        transactionIntent.validatePrice(transactionIntent.price!!.price!!, lastPrice!!.price!!)
        transactionIntentRepository.save(transactionIntent)

        val user = userRepository.findById(id).getOrElse { throw UsernameNotFoundException("User with id $id was not found") }
        user.addTransactionIntent(transactionIntent)
        userRepository.save(user)

        return transactionIntent
    }
}