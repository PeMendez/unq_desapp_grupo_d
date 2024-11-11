package com.desapp.crypto_exchange.service

import com.desapp.crypto_exchange.model.User
import org.springframework.security.core.userdetails.UserDetailsService

interface UserService : UserDetailsService{

    fun findUserByEmail(email: String): User?
    fun findUserById(id: Long): User
}
