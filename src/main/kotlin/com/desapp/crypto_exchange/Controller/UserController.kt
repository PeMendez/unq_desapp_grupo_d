package com.desapp.crypto_exchange.controller

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
    fun registerUser(@RequestBody user: User): ResponseEntity<User> {
        val newUser = userService.registerUser(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser)
    }

    @ExceptionHandler(UsernameAlreadyTakenException::class)
    fun handleUserException(e: UsernameAlreadyTakenException): ResponseEntity<String> {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
    }
}
