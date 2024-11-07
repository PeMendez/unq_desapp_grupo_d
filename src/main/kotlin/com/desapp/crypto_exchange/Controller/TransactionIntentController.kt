package com.desapp.crypto_exchange.Controller

import com.desapp.crypto_exchange.DTOs.ResponseTransactionIntentDTO
import com.desapp.crypto_exchange.DTOs.TransactionIntentDTO
import com.desapp.crypto_exchange.Mapper.TransactionIntentMapper
import com.desapp.crypto_exchange.Service.TransactionIntentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController {

    @Autowired
    lateinit var transactionIntentService: TransactionIntentService
    @Operation(
        summary = "Publish a transaction intent",
        description = "Creates a new transaction intent for a purchase/sale intent."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Intent posted successfully"),
            ApiResponse(
                responseCode = "403",
                description = "User not logged in or doesn't have permissions",
                content = [Content()]
            )
        ]
    )

    @PostMapping("/create/{userId}")
    fun createTransaction(@PathVariable userId: String, @RequestBody transactionDTO: TransactionIntentDTO): ResponseTransactionIntentDTO {
        val transactionIntent = TransactionIntentMapper.toModel(transactionDTO)
        val transactionIntentCreated = transactionIntentService.createTransactionIntent(transactionIntent, userId.toLong())
        return TransactionIntentMapper.toDTO(transactionIntentCreated)
    }
}