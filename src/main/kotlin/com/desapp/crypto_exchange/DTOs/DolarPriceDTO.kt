package com.desapp.crypto_exchange.DTOs

data class DolarPriceDTO(
    val moneda: String,
    val casa: String,
    val nombre: String,
    val compra: String,
    val venta: String,
    val fechaActualizacion: String
)