package com.desapp.crypto_exchange.service

import com.desapp.crypto_exchange.DTOs.DolarPriceDTO
import com.desapp.crypto_exchange.Helpers.TransactionIntentBuilder
import com.desapp.crypto_exchange.model.CryptoActive
import com.desapp.crypto_exchange.model.Price
import com.desapp.crypto_exchange.model.User
import com.desapp.crypto_exchange.repository.PriceRepository
import com.desapp.crypto_exchange.repository.TransactionIntentRepository
import com.desapp.crypto_exchange.repository.UserRepository
import com.desapp.crypto_exchange.service.Impl.TransactionIntentServiceImpl
import com.desapp.crypto_exchange.utils.PriceOutOfRangeException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*


@ExtendWith(SpringExtension::class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionIntentServiceTest {

    @Autowired
    private lateinit var transactionIntentService: TransactionIntentServiceImpl

    @MockBean
    private lateinit var transactionIntentRepository: TransactionIntentRepository

    @MockBean
    private lateinit var priceRepository: PriceRepository

    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var dollarService: DolarService


    @Test
    fun `when creating transaction intent, it should calculate ARS amount correctly`() {
        val mockUser = User()
        `when`(userRepository.findById(1L)).thenReturn(Optional.of(mockUser))

        val transactionIntent = TransactionIntentBuilder()
            .withOwner(mockUser)
            .withPrice(100f)
            .withCryptoActive(CryptoActive.ADAUSDT)
            .withAmount(1f)
            .build()

        val price = Price(cryptoActive = CryptoActive.ADAUSDT, price = 100f)
        `when`(priceRepository.findByCryptoActive(CryptoActive.ADAUSDT)).thenReturn(price)


        val dolarRate = DolarPriceDTO(
            moneda = "USD",
            casa = "cripto",
            nombre = "Cripto",
            compra = "1115",
            venta = "1118.47",
            fechaActualizacion = "2024-11-26T17:57:00.000Z"
        )
        `when`(dollarService.getDolarCryptoPrice()).thenReturn(dolarRate)

        val result = transactionIntentService.createTransactionIntent(transactionIntent, 1L)

        val expectedPriceInArs = 100F * 1F * 1115F // Price * Amount * Compra
        assertEquals(expectedPriceInArs, result.priceInArs)
    }

    @Test
    fun `when price is null, should throw IllegalArgumentException`() {
        val mockUser = User()
        `when`(userRepository.findById(1L)).thenReturn(Optional.of(mockUser))

        val transactionIntent = TransactionIntentBuilder()
            .withOwner(mockUser)
            .withPrice(null)
            .withCryptoActive(CryptoActive.ADAUSDT)
            .withAmount(1f)
            .build()

        val dolarRate = DolarPriceDTO(
            moneda = "USD",
            casa = "cripto",
            nombre = "Cripto",
            compra = "1115",
            venta = "1118.47",
            fechaActualizacion = "2024-11-26T17:57:00.000Z"
        )
        `when`(dollarService.getDolarCryptoPrice()).thenReturn(dolarRate)




        // Act & Assert
        val exception = assertThrows<IllegalArgumentException> {
            transactionIntentService.createTransactionIntent(transactionIntent, 1L)
        }

        assertEquals("TransactionIntent price cannot be null. Please ensure it is set before calling this method.", exception.message)
    }

    @Test
    fun `when cryptoActive is null, should throw IllegalArgumentException`() {
        val mockUser = User()
        `when`(userRepository.findById(1L)).thenReturn(Optional.of(mockUser))

        val transactionIntent = TransactionIntentBuilder()
            .withOwner(mockUser)
            .withCryptoActive(null)
            .withPrice(50f)
            .withAmount(1f)
            .build()

        val price = Price(cryptoActive = CryptoActive.ADAUSDT, price = 50f)
        `when`(priceRepository.findByCryptoActive(CryptoActive.ADAUSDT)).thenReturn(price)


        val dolarRate = DolarPriceDTO(
            moneda = "USD",
            casa = "cripto",
            nombre = "Cripto",
            compra = "1115",
            venta = "1118.47",
            fechaActualizacion = "2024-11-26T17:57:00.000Z"
        )
        `when`(dollarService.getDolarCryptoPrice()).thenReturn(dolarRate)

        val exception = assertThrows<IllegalArgumentException> {
            transactionIntentService.createTransactionIntent(transactionIntent, 1L)
        }
        assertEquals("TransactionIntent cryptoActive cannot be null.", exception.message)

    }

    @Test
    fun `when user not found, should throw UsernameNotFoundException`() {
        val transactionIntent = TransactionIntentBuilder()
            .withOwner(null)
            .withPrice(50f)
            .withCryptoActive(CryptoActive.ADAUSDT)
            .withAmount(1f)
            .build()

        `when`(userRepository.findById(1L)).thenReturn(Optional.empty())

        val exception = assertThrows<UsernameNotFoundException> {
            transactionIntentService.createTransactionIntent(transactionIntent, 1L)
        }

        assertEquals("User with id 1 was not found", exception.message)
    }

    @Test
    fun `when price is out of range, should throw PriceOutOfRangeException`() {
        val mockUser = User()
        `when`(userRepository.findById(1L)).thenReturn(Optional.of(mockUser))

        val transactionIntent = TransactionIntentBuilder()
            .withOwner(mockUser)
            .withPrice(200f)
            .withCryptoActive(CryptoActive.ADAUSDT)
            .withAmount(1f)
            .build()

        val price = Price(cryptoActive = CryptoActive.ADAUSDT, price = 100f)
        `when`(priceRepository.findByCryptoActive(CryptoActive.ADAUSDT)).thenReturn(price)


        val dolarRate = DolarPriceDTO(
            moneda = "USD",
            casa = "cripto",
            nombre = "Cripto",
            compra = "1115",
            venta = "1118.47",
            fechaActualizacion = "2024-11-26T17:57:00.000Z"
        )
        `when`(dollarService.getDolarCryptoPrice()).thenReturn(dolarRate)

        val exception = assertThrows<PriceOutOfRangeException> {
            transactionIntentService.createTransactionIntent(transactionIntent, 1L)
        }

        assertEquals("El precio de la intención no está dentro del margen de +/- 5% del precio actual.", exception.message)
    }
}

