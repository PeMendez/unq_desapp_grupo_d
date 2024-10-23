package com.desapp.crypto_exchange.DTOs

import com.desapp.crypto_exchange.model.Price

class CryptoPriceDTO (
    val symbol: String,
    val price: Float
)

fun Price.toDTO(): CryptoPriceDTO {
    return CryptoPriceDTO(
        symbol = this.cryptoActive.toString(),
        price = this.price!!
    )
}