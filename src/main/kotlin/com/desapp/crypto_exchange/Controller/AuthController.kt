package com.desapp.crypto_exchange.controller

import com.desapp.crypto_exchange.DTOs.LoginDTO
import com.desapp.crypto_exchange.DTOs.RegisterDTO
import com.desapp.crypto_exchange.Exceptions.InvalidLoginException
import com.desapp.crypto_exchange.Exceptions.UserCannotBeRegisteredException
import com.desapp.crypto_exchange.Exceptions.UserNotRegisteredException
import com.desapp.crypto_exchange.Exceptions.UsernameAlreadyTakenException
import com.desapp.crypto_exchange.model.User
import com.desapp.crypto_exchange.service.AuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class AuthController {

    @Autowired
    lateinit var authenticationService: AuthenticationService

    @PostMapping("/register")
    fun registerUser(@RequestBody registerDTO: RegisterDTO): ResponseEntity<String> {
        val newUser = User(registerDTO.firstName, registerDTO.lastName, registerDTO.email, registerDTO.password, registerDTO.address, registerDTO.cvu, registerDTO.cryptoWalletAddress)
        val response = authenticationService.registerUser(newUser)
        return ResponseEntity.ok(response)
    }

    @ExceptionHandler(UsernameAlreadyTakenException::class)
    fun handleUserException(e: UsernameAlreadyTakenException): ResponseEntity<String> {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
    }

    @ExceptionHandler(UserCannotBeRegisteredException::class)
    fun handleUserException(e: UserCannotBeRegisteredException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO): ResponseEntity<String> {
        val result = authenticationService.loginUser(loginDTO)
        return ResponseEntity.ok(result)
    }

    @ExceptionHandler(InvalidLoginException::class)
    fun handleInvalidLoginException(e: InvalidLoginException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.message)
    }

    @ExceptionHandler(UserNotRegisteredException::class)
    fun handleInvalidLoginException(e: UserNotRegisteredException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.message)
    }

}
