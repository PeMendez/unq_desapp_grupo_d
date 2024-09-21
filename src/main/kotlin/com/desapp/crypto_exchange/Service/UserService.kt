package com.desapp.crypto_exchange.service

import com.desapp.crypto_exchange.model.User

interface UserService {

    fun registerUser(user: User): User
}
