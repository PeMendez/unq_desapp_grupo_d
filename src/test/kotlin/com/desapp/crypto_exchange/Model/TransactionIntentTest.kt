package com.desapp.crypto_exchange.Model

import com.desapp.crypto_exchange.Helpers.PriceBuilder
//import com.desapp.crypto_exchange.Helpers.TransactionIntentBuilder
import com.desapp.crypto_exchange.Helpers.UserBuilder
import com.desapp.crypto_exchange.model.OperationType
import com.desapp.crypto_exchange.model.Price
import com.desapp.crypto_exchange.model.User
import com.desapp.crypto_exchange.utils.PriceOutOfRangeException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TransactionIntentTest {

    /*@Test
    fun `shouldAllowTransactionIntentWithinFivePercentPriceMargin`(){
        val user : User = UserBuilder().build()
        val price = 1000f
        val transactionIntent = TransactionIntentBuilder()
            .withOwner(user)
            .withPrice(price)
            .withAmount(100f)
            .withOperationType(OperationType.SALE)
            .withActive(true)
            .build()

        val lastPrice = price
        val lowerBound = lastPrice * 0.95f  // Límite inferior (95% del precio actual)
        val upperBound = lastPrice * 1.05f  // Límite superior (105% del precio actual)

        val intentPrice = transactionIntent.price!!
        assertTrue(intentPrice in lowerBound..upperBound)
        assertTrue(transactionIntent.active == true)
    }

    @Test
    fun `shouldBanTansactionIntenOutsideFivePercentPriceMargin`(){
        val user : User = UserBuilder().build()
        val price = 1000f
        val transactionIntent = TransactionIntentBuilder()
            .withOwner(user)
            .withPrice(price)
            .withAmount(100f)
            .withOperationType(OperationType.SALE)
            .withActive(true)
            .build()

        val intentPrice = 110f

        val exception = assertThrows<PriceOutOfRangeException> {
            transactionIntent.validatePrice(intentPrice, price)
        }

        assertEquals("El precio de la intención no está dentro del margen de +/- 5% del precio actual.", exception.message)
    }*/
}