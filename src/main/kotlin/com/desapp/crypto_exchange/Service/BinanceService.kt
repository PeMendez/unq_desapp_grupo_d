package com.desapp.crypto_exchange.Service

import com.desapp.crypto_exchange.DTOs.CryptoPriceDTO
import com.desapp.crypto_exchange.Exceptions.ApiNotResponding
import com.desapp.crypto_exchange.model.CryptoActive
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate

@Service
class BinanceService() {

    private val baseUrl = "https://api1.binance.com/api/v3/ticker/price"

    @Autowired
    lateinit var restTemplate: RestTemplate

    fun getPriceForCrypto(symbol: String) : CryptoPriceDTO?{
        val url = "$baseUrl?symbol=$symbol"
        return try {
            val response= restTemplate.getForObject(url, CryptoPriceDTO::class.java)
            response
        }catch(e: RestClientException){
            throw ApiNotResponding("")
        }
    }

    fun getPrices(symbols: List<String>): List<CryptoPriceDTO> {
        val formattedSymbols = symbols.joinToString(",") { "\"$it\"" }
        val url = "$baseUrl?symbols=[$formattedSymbols]"
        return try {
            val response = restTemplate.getForObject(url, Array<CryptoPriceDTO>::class.java)
            response?.toList() ?: emptyList()
        } catch (e: RestClientException) {

            throw ApiNotResponding("")

        }
    }

    fun getAllPrices(): List<CryptoPriceDTO>{
        val symbolList = CryptoActive.entries.map{it.name}
        return getPrices(symbolList.filterNot{it=="USDAR"})
    }
}