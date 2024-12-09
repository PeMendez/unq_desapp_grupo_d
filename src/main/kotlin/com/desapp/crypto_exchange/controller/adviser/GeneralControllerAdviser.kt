package com.desapp.crypto_exchange.controller.adviser

import com.desapp.crypto_exchange.Exceptions.UserCannotBeRegisteredException
import com.desapp.crypto_exchange.Exceptions.UsernameAlreadyTakenException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GeneralControllerAdviser {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @ExceptionHandler(UsernameAlreadyTakenException::class)
    fun handleRunTimeException(e: RuntimeException): ResponseEntity<String> {
        return this.error(CONFLICT, e)
    }

    @ExceptionHandler(UsernameNotFoundException::class)
    fun handleNotFoundException(e: RuntimeException): ResponseEntity<String> {
        return this.error(NOT_FOUND, e)
    }

    @ExceptionHandler(UserCannotBeRegisteredException::class)
    fun handleWrongUserRegister(e: RuntimeException): ResponseEntity<String> {
        return this.error(BAD_REQUEST, e)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleOutOfPriceException(e: RuntimeException): ResponseEntity<String> {
        return this.error(BAD_REQUEST, e)
    }
    protected fun error(status: HttpStatus, e: RuntimeException): ResponseEntity<String> {
        logger.error(e.message)
        return ResponseEntity.status(status).body(e.message)
    }
}