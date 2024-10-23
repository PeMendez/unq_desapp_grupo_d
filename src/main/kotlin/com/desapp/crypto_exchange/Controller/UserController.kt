package com.desapp.crypto_exchange.controller

import com.desapp.crypto_exchange.DTOs.UserDTO
import com.desapp.crypto_exchange.Exceptions.UserCannotBeRegisteredException
import com.desapp.crypto_exchange.Exceptions.UsernameAlreadyTakenException
import com.desapp.crypto_exchange.model.User
import com.desapp.crypto_exchange.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody userDTO: UserDTO): User {
        val newUser = User(userDTO.firstName, userDTO.lastName, userDTO.email, userDTO.password, userDTO.address, userDTO.cvu, userDTO.cryptoWalletAddress)
        return userService.registerUser(newUser)
    }

    @ExceptionHandler(UsernameAlreadyTakenException::class)
    fun handleUserException(e: UsernameAlreadyTakenException): ResponseEntity<String> {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
    }

    @ExceptionHandler(UserCannotBeRegisteredException::class)
    fun handleUserException(e: UserCannotBeRegisteredException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
    }

}
