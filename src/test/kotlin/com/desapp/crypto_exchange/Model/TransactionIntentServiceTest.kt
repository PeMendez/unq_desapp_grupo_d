package com.desapp.crypto_exchange.Model

import com.desapp.crypto_exchange.Helpers.PriceBuilder
import com.desapp.crypto_exchange.Helpers.TransactionIntentBuilder
import com.desapp.crypto_exchange.Helpers.UserBuilder
import com.desapp.crypto_exchange.model.OperationType
import com.desapp.crypto_exchange.model.Price
import com.desapp.crypto_exchange.model.User
import com.desapp.crypto_exchange.utils.PriceOutOfRangeException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TransactionIntentServiceTest {

    @Test
    fun `shouldAllowTransactionIntentWithinFivePercentPriceMargin`(){
        val user : User = UserBuilder().build()
        val price: Price = PriceBuilder().withPrice(1000f).build()
        val transactionIntent = TransactionIntentBuilder()
            .withOwner(user)
            .withPrice(price)
            .withAmount(100f)
            .withOperationType(OperationType.SALE)
            .withActive(true)
            .build()

        val lastPrice = price.price!!
        val lowerBound = lastPrice * 0.95f  // Límite inferior (95% del precio actual)
        val upperBound = lastPrice * 1.05f  // Límite superior (105% del precio actual)

        val intentPrice = transactionIntent.price?.price!!
        assertTrue(intentPrice in lowerBound..upperBound)
        assertTrue(transactionIntent.active == true)
    }

    @Test
    fun `shouldBanTansactionIntenOutsideFivePercentPriceMargin`(){
        val user : User = UserBuilder().build()
        val price: Price = PriceBuilder().withPrice(1000f).build()
        val transactionIntent = TransactionIntentBuilder()
            .withOwner(user)
            .withPrice(price)
            .withAmount(100f)
            .withOperationType(OperationType.SALE)
            .withActive(true)
            .build()

        val intentPrice = 110f

        val exception = assertThrows<PriceOutOfRangeException> {
            transactionIntent.validatePrice(intentPrice, price.price!!)
        }

        assertEquals("El precio de la intención no está dentro del margen de +/- 5% del precio actual.", exception.message)
    }
}