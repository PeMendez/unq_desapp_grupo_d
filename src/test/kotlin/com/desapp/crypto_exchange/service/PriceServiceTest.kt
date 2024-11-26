package com.desapp.crypto_exchange.service

import com.desapp.crypto_exchange.DTOs.CryptoPriceDTO
import com.desapp.crypto_exchange.DTOs.DolarPriceDTO
import com.desapp.crypto_exchange.model.CryptoActive
import com.desapp.crypto_exchange.model.Price
import com.desapp.crypto_exchange.repository.PriceRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootTest
class PriceServiceTest {
    @MockBean
    lateinit var priceRepository: PriceRepository

    @MockBean
    lateinit var binanceApiService: BinanceService

    @MockBean
    lateinit var dolarApiService: DolarService

    @Autowired
    lateinit var priceService: PriceService

    @Autowired
    lateinit var cacheManager: CacheManager

    @BeforeEach
    fun setup() {
        // Clear cache before each test
        cacheManager.getCache("prices")?.clear()
    }
    @Test
    fun `test getAllPrices cache`() {
        // Mock repository data
        val mockPrices = listOf(Price(CryptoActive.BTCUSDT, 10000.0f), Price(CryptoActive.ETHUSDT, 2000.0f))
        Mockito.`when`(priceRepository.findAll()).thenReturn(mockPrices)

        // Call the service method and verify cache
        val firstCall = priceService.getPrices()
        val secondCall = priceService.getPrices()

        // Verify that the repository method was called only once
        Mockito.verify(priceRepository, Mockito.times(1)).findAll()

        // Verify the data returned is correct
        assertEquals(mockPrices, firstCall)
        assertEquals(mockPrices, secondCall)
    }

    @Test
    fun `test getPrices cache`() {
        // Mock repository data
        val crypto = CryptoActive.BTCUSDT
        val mockPrices = listOf(Price(crypto, 10000.0f))
        Mockito.`when`(priceRepository.findAllByCryptoActive(crypto)).thenReturn(mockPrices)

        // Call the service method and verify cache
        val firstCall = priceService.getPricesWithCryptoActive(crypto)
        val secondCall = priceService.getPricesWithCryptoActive(crypto)

        // Verify that the repository method was called only once
        Mockito.verify(priceRepository, Mockito.times(1)).findAllByCryptoActive(crypto)

        // Verify the data returned is correct
        assertEquals(mockPrices, firstCall)
        assertEquals(mockPrices, secondCall)
    }

    @Test
    fun `test getPrices cache distintos`() {
        // Mock repository data
        val crypto = CryptoActive.BTCUSDT
        val mockPrices = listOf(Price(crypto, 10000.0f))
        Mockito.`when`(priceRepository.findAllByCryptoActive(crypto)).thenReturn(mockPrices)

        // Call the service method and verify cache
        val firstCall = priceService.getPricesWithCryptoActive(crypto)
        val thirdCall = priceService.getPricesWithCryptoActive(CryptoActive.ALICEUSDT)
        val secondCall = priceService.getPricesWithCryptoActive(crypto)

        // Verify that the repository method was called only once
        Mockito.verify(priceRepository, Mockito.times(1)).findAllByCryptoActive(crypto)

        // Verify the data returned is correct
        assertEquals(mockPrices, firstCall)
        assertEquals(mockPrices, secondCall)
        assertNotEquals(mockPrices, thirdCall)
    }

//    @Test
//    fun `test updatePrices updates cache`() {
//        // Mock repository data
//        val mockResponse = DolarPriceDTO(
//            "USD","cripto","Cripto","101", "100",
//            "2024-05-15T20:57:00.000Z"
//        )
//        val binancePrices = listOf(
//            CryptoPriceDTO("BTCUSDT", 1000f), CryptoPriceDTO("ETHUSDT", 2000f))
//        Mockito.`when`(binanceApiService.getAllPrices()).thenReturn(binancePrices)
//        Mockito.`when`(dolarApiService.getDolarCryptoPrice()).thenReturn(mockResponse)
//
//        // Call the service method and verify cache
//        priceService.updatePrice()
//
//        // Verify that the repository method was called
//        Mockito.verify(priceRepository, Mockito.times(1)).saveAll(Mockito.anyList())
//
//        val names = cacheManager.cacheNames
//        // Verify that the cache was updated
//        val cache = cacheManager.getCache(names.first())
//
//        val cachedPrices = cache?.get("allPrices")?.get() as? List<*>
//        assertNotNull(cachedPrices)
//
//        assertEquals(3, cachedPrices!!.size)
//    }
}
