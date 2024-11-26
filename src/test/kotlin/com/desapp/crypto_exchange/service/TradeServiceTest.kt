package com.desapp.crypto_exchange.service

import com.desapp.crypto_exchange.Helpers.PriceBuilder
import com.desapp.crypto_exchange.Helpers.TradeBuilder
import com.desapp.crypto_exchange.Helpers.TransactionIntentBuilder
import com.desapp.crypto_exchange.Helpers.UserBuilder
import com.desapp.crypto_exchange.service.Impl.TradeServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals

import com.desapp.crypto_exchange.model.*
import com.desapp.crypto_exchange.repository.PriceRepository
import com.desapp.crypto_exchange.repository.TradeRepository
import com.desapp.crypto_exchange.repository.TransactionIntentRepository
import com.desapp.crypto_exchange.repository.UserRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.*

@SpringBootTest
class TradeServiceTest {

    @Autowired
    lateinit var tradeService: TradeServiceImpl

    @MockBean
    lateinit var tradeRepository: TradeRepository

    @MockBean
    lateinit var transactionIntentRepository: TransactionIntentRepository

    @MockBean
    lateinit var priceRepository: PriceRepository

    @MockBean
    lateinit var userRepository: UserRepository

    private lateinit var buyer: User
    private lateinit var seller: User
    private lateinit var transactionIntent: TransactionIntent
    private lateinit var price: Price

    @BeforeEach
    fun setUp() {
        // Verificar los valores de los objetos antes de usarlos
        buyer = UserBuilder()
            .withId(1L)
            .withFirstName("John")
            .withLastName("Doe")
            .build()
        println("Buyer ID: ${buyer.id}")

        seller = UserBuilder()
            .withId(2L)
            .withFirstName("Jane")
            .withLastName("Smith")
            .build()
        println("Seller ID: ${seller.id}")

         transactionIntent = TransactionIntentBuilder()
            .withOwner(seller)
            .withId(1L)
            .withPrice(10f)
            .withCryptoActive(CryptoActive.ADAUSDT)
            .withAmount(100f)
            .build()
        println("Transaction Intent ID: ${transactionIntent.id}")
        println("Transaction Intent CryptoActive: ${transactionIntent.cryptoActive}")

        price = PriceBuilder()
            .withCryptoActive(CryptoActive.BNBUSDT)
            .withPrice(10F)
            .build()
        println("Price CryptoActive: ${price.cryptoActive}")

        `when`(userRepository.findById(buyer.id!!)).thenReturn(Optional.of(buyer))
        `when`(transactionIntentRepository.findById(transactionIntent.id!!)).thenReturn(Optional.of(transactionIntent))
        `when`(priceRepository.findByCryptoActive(transactionIntent.cryptoActive!!)).thenReturn(price)
    }


    @Test
    fun `should create trade successfully`() {
        val trade = tradeService.createTrade(buyer.id!!, transactionIntent.id!!)

        assertEquals(buyer, trade.buyer)
        assertEquals(seller, trade.seller)
        assertEquals(transactionIntent, trade.transactionIntent)
        assertEquals(100F, trade.nominalAmount)
        assertEquals(10F, trade.tradePrice)
        assertEquals(OperationType.SALE, trade.tradeType)
    }

    @Test
    fun `should throw exception when buyer is not found`() {
        `when`(userRepository.findById(buyer.id!!)).thenReturn(java.util.Optional.empty())

        val exception = assertThrows<UsernameNotFoundException> {
            tradeService.createTrade(buyer.id!!, 1L)
        }

        assertEquals("Buyer with id 1 was not found", exception.message)
    }

    @Test
    fun `should throw exception when transactionIntent is not found`() {
        `when`(transactionIntentRepository.findById(transactionIntent.id!!)).thenReturn(java.util.Optional.empty())

        val exception = assertThrows<IllegalArgumentException> {
            tradeService.createTrade(buyer.id!!, transactionIntent.id!!)
        }

        assertEquals("TransactionIntent with id 1 does not exist.", exception.message)
    }

    @Test
    fun `should complete trade successfully`() {
        val trade = TradeBuilder()
            .withId(1L)
            .withBuyer(buyer)
            .withSeller(seller)
            .withTransactionIntent(transactionIntent)
            .withCryptoActive(CryptoActive.ADAUSDT)
            .withNominalAmount(100F)
            .withTradePrice(10F)
            .withTradeType(OperationType.SALE)
            .withTradeStatus(TradeStatus.PENDING)
            .build()

        `when`(tradeRepository.findById(trade.id!!)).thenReturn(Optional.of(trade))

        tradeService.completeTrade(trade.id!!)

        assertEquals(TradeStatus.COMPLETED, trade.tradeStatus)
        verify(tradeRepository, times(1)).save(trade)
    }


    @Test
    fun `should throw exception when trade to complete is not found`() {
        val tradeId = 1L

        `when`(tradeRepository.findById(tradeId)).thenReturn(Optional.empty())

        val exception = assertThrows<IllegalArgumentException> {
            tradeService.completeTrade(tradeId)
        }

        assertEquals("Trade with id $tradeId does not exist.", exception.message)
        verify(tradeRepository, never()).save(any(Trade::class.java))
    }

    @Test
    fun `should cancel trade successfully`() {
        val trade = TradeBuilder()
            .withId(1L)
            .withBuyer(buyer)
            .withSeller(seller)
            .withTransactionIntent(transactionIntent)
            .withCryptoActive(CryptoActive.ADAUSDT)
            .withNominalAmount(100F)
            .withTradePrice(10F)
            .withTradeType(OperationType.SALE)
            .withTradeStatus(TradeStatus.PENDING)
            .build()

        `when`(tradeRepository.findById(trade.id!!)).thenReturn(Optional.of(trade))

        tradeService.cancelTrade(trade.id!!)

        assertEquals(TradeStatus.CANCELLED, trade.tradeStatus)
        verify(tradeRepository, times(1)).save(trade)
    }


    @Test
    fun `should throw exception when trade to cancel is not found`() {
        val tradeId = 1L

        `when`(tradeRepository.findById(tradeId)).thenReturn(Optional.empty())

        val exception = assertThrows<IllegalArgumentException> {
            tradeService.cancelTrade(tradeId)
        }

        assertEquals("Trade with id $tradeId does not exist.", exception.message)
        verify(tradeRepository, never()).save(any(Trade::class.java))
    }

    @Test
    fun `should return all trades`() {
        val trade1 = Trade(
            buyer = buyer,
            seller = seller,
            transactionIntent = transactionIntent,
            nominalAmount = 100F,
            tradePrice = 10F,
            tradeType = OperationType.SALE,
            tradeStatus = TradeStatus.PENDING,
            cryptoActive = CryptoActive.ADAUSDT
        )
        val trade2 = Trade(
            buyer = buyer,
            seller = seller,
            transactionIntent = transactionIntent,
            nominalAmount = 200F,
            tradePrice = 20F,
            tradeType = OperationType.PURCHASE,
            tradeStatus = TradeStatus.PENDING,
            cryptoActive = CryptoActive.ADAUSDT
        )
        val trades = listOf(trade1, trade2)

        `when`(tradeRepository.findAll()).thenReturn(trades)

        val result = tradeService.getAllTrades()

        assertEquals(trades.size, result.size)
        assertEquals(trade1, result[0])
        assertEquals(trade2, result[1])
        verify(tradeRepository, times(1)).findAll()
    }

}
