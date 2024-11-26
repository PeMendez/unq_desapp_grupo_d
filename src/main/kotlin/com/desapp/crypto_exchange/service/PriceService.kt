package com.desapp.crypto_exchange.service

import com.desapp.crypto_exchange.model.CryptoActive
import com.desapp.crypto_exchange.model.Price
import org.springframework.cache.annotation.Cacheable

interface PriceService {

    fun getPrices(): List<Price>
    fun updatePrice()
    fun getPricesWithCryptoActive(cryptoActive: CryptoActive): List<Price>

}