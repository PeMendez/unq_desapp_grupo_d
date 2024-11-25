package com.desapp.crypto_exchange.service.Impl

import com.desapp.crypto_exchange.model.User
import com.desapp.crypto_exchange.repository.UserRepository
import com.desapp.crypto_exchange.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {

    @Autowired
    lateinit var userDao: UserRepository
    override fun findUserByEmail(email: String): User? {
        return userDao.findByEmail(email)
    }

    override fun findUserById(id: Long): User {
        return userDao.findById(id).get()
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val userdetails: User= userDao.findByEmail(username!!)
            ?: throw UsernameNotFoundException("User with email $username does not exist.")

        val authorities: Set<GrantedAuthority> = listOf(SimpleGrantedAuthority("ROLE_USER") ).toSet()
        return org.springframework.security.core.userdetails.User(
            userdetails.email,
            userdetails.password,
            true,
            true,
            true,
            true,
            authorities
        )
    }
}



