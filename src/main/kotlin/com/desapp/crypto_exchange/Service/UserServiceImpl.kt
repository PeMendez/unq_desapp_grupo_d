package com.desapp.crypto_exchange.Service

import com.desapp.crypto_exchange.Exceptions.UsernameAlreadyTakenException
import com.desapp.crypto_exchange.model.User
import com.desapp.crypto_exchange.repository.UserRepository
import com.desapp.crypto_exchange.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserServiceImpl : UserService {

    @Autowired
    lateinit var userDao: UserRepository


    override fun registerUser(user: User): User {
        return userDao.findByEmail(user.email!!)?: throw UsernameAlreadyTakenException("Email ${user.email} has already taken")
    }
}



