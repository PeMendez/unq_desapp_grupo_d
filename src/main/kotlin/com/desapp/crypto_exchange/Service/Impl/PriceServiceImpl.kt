package com.desapp.crypto_exchange.Service.Impl

import com.desapp.crypto_exchange.Repository.PriceRepository
import com.desapp.crypto_exchange.Service.BinanceService
import com.desapp.crypto_exchange.Service.PriceService
import com.desapp.crypto_exchange.model.CryptoActive
import com.desapp.crypto_exchange.model.Price
import org.springframework.beans.factory.annotation.Autowired
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Transactional
@Service
class PriceServiceImpl : PriceService {

    @Autowired
    lateinit var priceRepository: PriceRepository

    @Autowired
    lateinit var binanceService: BinanceService


    override fun getPrices(): List<Price> {
        return priceRepository.findAll()
    }

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


}