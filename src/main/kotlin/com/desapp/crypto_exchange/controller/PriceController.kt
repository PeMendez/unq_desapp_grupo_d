package com.desapp.crypto_exchange.controller

import com.desapp.crypto_exchange.service.PriceService
import com.desapp.crypto_exchange.model.Price
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/price")
class PriceController {

    @Autowired
    lateinit var priceService: PriceService

    @GetMapping("/getPrices")
    fun getPrices(): List<Price>{
        return priceService.getPrices()
    }

    @PostMapping("/updatePrices")
    fun updatePrices(): String {
        try {
            priceService.updatePrice()
            return "OK"
        } catch (e: Exception) {
            return "Ouch"
        }
    }

}