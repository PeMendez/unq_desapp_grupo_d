package com.desapp.crypto_exchange.DTOs

import io.swagger.v3.oas.annotations.media.Schema
@Schema(description = "Login credentials for users.")
data class LoginDTO(
    @Schema(description = "User email", example = "a_valid@email.com")
    val email: String,
    @Schema(description = "Password for user", example = "P@ssW0Rd")
    val password: String)