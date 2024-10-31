package com.desapp.crypto_exchange.Service

import com.desapp.crypto_exchange.DTOs.DolarPriceDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class DolarService {

    private val baseUrl = "https://dolarapi.com/v1/dolares/cripto"

    @Autowired
    lateinit var restTemplate: RestTemplate

    fun getDolarCryptoPrice(): DolarPriceDTO?{
        return restTemplate.getForObject(baseUrl, DolarPriceDTO ::class.java)
    }
}