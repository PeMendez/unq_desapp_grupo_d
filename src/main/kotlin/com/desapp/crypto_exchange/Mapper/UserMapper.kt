package com.desapp.crypto_exchange.Mapper

import com.desapp.crypto_exchange.DTOs.UserDTO
import com.desapp.crypto_exchange.model.User

class UserMapper {
    companion object {
            fun toDTO(user: User): UserDTO {
                return UserDTO(
                    id = user.id ?: 0L,
                    firstName = user.firstName,
                    lastName = user.lastName,
                    email = user.email ?: "",
                    address = user.address,
                    cvu = user.cvu,
                    cryptoWalletAddress = user.cryptoWalletAddress
                )
            }
    }
}