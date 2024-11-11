package com.desapp.crypto_exchange.service

import com.desapp.crypto_exchange.model.User
import com.desapp.crypto_exchange.repository.UserRepository
import com.desapp.crypto_exchange.Exceptions.UsernameAlreadyTakenException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
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

//    @Autowired
//    lateinit var authenticationManager: AuthenticationManager

    fun registerUser(user: User): String {
        return try {
            user.password = passwordEncoder.encode(user.password)
            userRepository.save(user)
            logger.info("User ${user.email} has been registered.")
            jwtService.createToken(user)
        } catch (e: DataIntegrityViolationException) {
            throw UsernameAlreadyTakenException("The email ${user.email} is already in use.")
        }
    }

//    fun loginUser(loginDTO: LoginDTO): String {
//        authenticationManager.authenticate(
//            UsernamePasswordAuthenticationToken(loginDTO.email, loginDTO.password)
//        )
//        val user = userRepository.findByEmail(loginDTO.email) ?: throw Exception("User not found")
//        logger.info("User ${user.email} logged in.")
//        return jwtService.createToken(user)
//    }
}
