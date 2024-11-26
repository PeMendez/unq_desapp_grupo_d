package com.desapp.crypto_exchange.controller

import com.desapp.crypto_exchange.DTOs.LoginDTO
import com.desapp.crypto_exchange.DTOs.RegisterDTO
import com.desapp.crypto_exchange.Helpers.MockitoHelper
import com.desapp.crypto_exchange.model.User
import com.desapp.crypto_exchange.service.AuthenticationService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var authenticationService: AuthenticationService

    @Test
    fun `registerUser should return OK for successful registration`() {
        val registerDTO = RegisterDTO(
            firstName = "Lalo",
            lastName = "Landa",
            email = "existing_email@email.com",
            password = "P4SS+_W0rd",
            address = "Calle falsa 123",
            cvu = "1234567890123456789012",
            cryptoWalletAddress = "19283746"
        )
        val user = User(
            registerDTO.firstName,
            registerDTO.lastName,
            registerDTO.email,
            registerDTO.password,
            registerDTO.address,
            registerDTO.cvu,
            registerDTO.cryptoWalletAddress
        )
        val expectedResponse = "User registered successfully!"

        `when`(authenticationService.registerUser(user)).thenReturn(expectedResponse)

        mockMvc.perform(
            post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(registerDTO))
        )
            .andDo(print())
            .andExpect(status().isOk)
    }

//    @Test
//    fun `registerUser should return BAD_REQUEST when username is already taken`() {
//        val email = "existing_email@email.com" // Nombre de usuario duplicado
//        val registerDTO = RegisterDTO(
//            firstName = "Lalo",
//            lastName = "Landa",
//            email = email,
//            password = "P4SS+_W0rd",
//            address = "Calle falsa 123",
//            cvu = "1234567890123456789012",
//            cryptoWalletAddress = "19283746",
//        )
//        val user = User(
//            registerDTO.firstName,
//            registerDTO.lastName,
//            registerDTO.email,
//            registerDTO.password,
//            registerDTO.address,
//            registerDTO.cvu,
//            registerDTO.cryptoWalletAddress
//        )
//        authenticationService.registerUser(user)
//
//        `when`(authenticationService.registerUser(MockitoHelper.anyObject()))
//            .thenThrow(UsernameAlreadyTakenException("The email ${email} is already in use."))
//
//        mockMvc.perform(
//            post("/register")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(ObjectMapper().writeValueAsString(registerDTO))
//        )
//            .andDo(print())
//            .andExpect(status().isBadRequest)
//            .andExpect(jsonPath("$").value("The email ${email} is already in use."))
//    }


    @Test
    fun `login should return OK for successful login`() {
        val loginDTO = LoginDTO(email = "john.doe@example.com", password = "securePassword")
        val tokenResponse = "JWT_TOKEN"

        `when`(authenticationService.loginUser(MockitoHelper.anyObject())).thenReturn(tokenResponse)

        mockMvc.perform(
            post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(loginDTO))
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").value(tokenResponse))
    }

//    @Test
//    fun `login should return UNAUTHORIZED for invalid credentials`() {
//        val loginDTO = LoginDTO(email = "john.doe@example.com", password = "wrongPassword")
//
//        Mockito.doThrow(InvalidLoginException("Invalid credentials"))
//            .`when`(authenticationService).loginUser(MockitoHelper.anyObject())
//
//
//        mockMvc.perform(
//            post("/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(ObjectMapper().writeValueAsString(loginDTO))
//        )
//            .andDo(print())
//            .andExpect(status().isUnauthorized)
//            .andExpect(jsonPath("$").value("Invalid credentials"))
//    }
}
