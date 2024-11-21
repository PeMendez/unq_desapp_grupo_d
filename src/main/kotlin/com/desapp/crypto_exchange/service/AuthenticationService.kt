package com.desapp.crypto_exchange.service

import com.desapp.crypto_exchange.DTOs.LoginDTO
import com.desapp.crypto_exchange.Exceptions.InvalidLoginException
import com.desapp.crypto_exchange.Exceptions.UserCannotBeRegisteredException
import com.desapp.crypto_exchange.Exceptions.UserNotRegisteredException
import com.desapp.crypto_exchange.model.User
import com.desapp.crypto_exchange.repository.UserRepository
import com.desapp.crypto_exchange.Exceptions.UsernameAlreadyTakenException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthenticationService {

    private val logger = LoggerFactory.getLogger(AuthenticationService::class.java)

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var jwtService: JwtService

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    fun registerUser(user: User): String {
        if (isUsernameTaken(user.email!!)) {
            throw UsernameAlreadyTakenException("The email ${user.email} is already in use.")
        }
        if (!canRegisterUser(user)) {
            throw UserCannotBeRegisteredException("User cannot be registered due to invalid data.")
        }
        user.password = passwordEncoder.encode(user.password)
        userRepository.save(user)

        logger.info("User ${user.email} has been registered.")
        logger.info("User id ${user.id}")
        return jwtService.createToken(user)
    }

    private fun isUsernameTaken(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    private fun canRegisterUser(user: User): Boolean {
        return user.cvu!!.isNotEmpty() && user.cryptoWalletAddress!!.isNotEmpty()
    }

    fun loginUser(loginDTO: LoginDTO): String {
        val user = userRepository.findByEmail(loginDTO.email)
            ?: throw UserNotRegisteredException("User with email ${loginDTO.email} not found.")

        if (!passwordEncoder.matches(loginDTO.password, user.password)) {
            throw InvalidLoginException("Invalid credentials.")
        }

        logger.info("User ${loginDTO.email} has logged in.")
        return jwtService.createToken(user)
    }

}
