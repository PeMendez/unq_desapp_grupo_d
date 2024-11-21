package com.desapp.crypto_exchange.service.Impl

import com.desapp.crypto_exchange.service.DolarService
import com.desapp.crypto_exchange.repository.TransactionIntentRepository
import com.desapp.crypto_exchange.service.TransactionIntentService
import com.desapp.crypto_exchange.model.TransactionIntent
import com.desapp.crypto_exchange.repository.PriceRepository
import com.desapp.crypto_exchange.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

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
    lateinit var dollarService: DolarService
    override fun createTransactionIntent(transactionIntent: TransactionIntent, id: Long): TransactionIntent {

        if (transactionIntent.price == null) {
            throw IllegalArgumentException("TransactionIntent price cannot be null. Please ensure it is set before calling this method.")
        }
        if (transactionIntent.cryptoActive == null) {
            throw IllegalArgumentException("TransactionIntent cryptoActive cannot be null.")
        }
        println("TransactionIntent: $transactionIntent")


        val user = userRepository.findById(id).orElseThrow {
            UsernameNotFoundException("User with id $id was not found")
        }
        println("User: $user")


        val price = priceRepository.findByCryptoActive(transactionIntent.cryptoActive!!)
            ?: throw IllegalArgumentException("No price found for crypto active: ${transactionIntent.cryptoActive}")
        println("Price: $price")


        val purchaseDollarPriceInArs = dollarService.getDolarCryptoPrice()?.compra?.toFloat()
            ?: throw IllegalStateException("No se pudo obtener el precio del dólar")
        println("Purchase Dollar Price in ARS: $purchaseDollarPriceInArs")


        transactionIntent.calculateArsAmount(purchaseDollarPriceInArs)


        transactionIntent.validatePrice(transactionIntent.price!!, price.price!!)


        transactionIntent.owner = user
        transactionIntentRepository.save(transactionIntent)


        user.addTransactionIntent(transactionIntent)
        userRepository.save(user)

        return transactionIntent
    }


}