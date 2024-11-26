package com.desapp.crypto_exchange.service.Impl

import com.desapp.crypto_exchange.repository.PriceRepository
import com.desapp.crypto_exchange.service.BinanceService
import com.desapp.crypto_exchange.service.PriceService
import com.desapp.crypto_exchange.model.CryptoActive
import com.desapp.crypto_exchange.model.Price
import org.springframework.beans.factory.annotation.Autowired
import jakarta.transaction.Transactional
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Transactional
@Service
@CacheConfig(cacheNames = ["prices"])
class PriceServiceImpl : PriceService {

    @Autowired
    lateinit var priceRepository: PriceRepository

    @Autowired
    lateinit var binanceService: BinanceService

    @Cacheable(cacheNames = ["prices"])
    override fun getPrices(): List<Price> {
        println("Fetching prices from repository...")
        return priceRepository.findAll()
    }

    @CachePut(cacheNames = ["prices"], key = "'allPrices'", condition = "True")
    override fun updatePrice() {
        val binancePrices = binanceService.getAllPrices()

        val existingPrices = priceRepository.findAll().associateBy { it.cryptoActive }

        val prices = binancePrices.map {
            val cryptoCurrency = CryptoActive.valueOf(it.symbol)
            val price = existingPrices[cryptoCurrency]?.apply {
                this.price = it.price
                this.dateTime = LocalDateTime.now()
            } ?: Price(cryptoCurrency, it.price)
            price
        }.toMutableList()

        priceRepository.saveAll(prices)
    }

    @Cacheable(cacheNames = ["prices"], key="'prices_'+#cryptoActive")
    override fun getPricesWithCryptoActive(cryptoActive: CryptoActive): List<Price> {
        return priceRepository.findAllByCryptoActive(cryptoActive)
    }


}