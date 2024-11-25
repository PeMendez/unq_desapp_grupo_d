package com.desapp.crypto_exchange.service

import com.desapp.crypto_exchange.model.Price

interface PriceService {

    fun getPrices(): List<Price>
    fun updatePrice()
}