package com.desapp.crypto_exchange.DTOs

data class UserDTO(
    val id: Long,
    val firstName: String?,
    val lastName: String?,
    val email: String,
    val address: String?,
    val cvu: String?,
    val cryptoWalletAddress: String?
)
