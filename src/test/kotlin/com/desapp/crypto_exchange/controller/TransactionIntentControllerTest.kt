package com.desapp.crypto_exchange.controller

import com.desapp.crypto_exchange.DTOs.DolarPriceDTO
import com.desapp.crypto_exchange.Helpers.MockitoHelper
import com.desapp.crypto_exchange.DTOs.TransactionIntentDTO
import com.desapp.crypto_exchange.Helpers.TransactionIntentBuilder
import com.desapp.crypto_exchange.Helpers.UserBuilder
import com.desapp.crypto_exchange.model.CryptoActive
import com.desapp.crypto_exchange.model.OperationType
import com.desapp.crypto_exchange.model.TradeStatus
import com.desapp.crypto_exchange.service.DolarService
import com.desapp.crypto_exchange.service.TransactionIntentService
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.`when`
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import java.time.LocalDateTime
import org.springframework.http.MediaType
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print


@SpringBootTest
@AutoConfigureMockMvc
class TransactionIntentControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var dollarService: DolarService

    @MockBean
    private lateinit var transactionIntentService: TransactionIntentService

    @Test
    @WithMockUser
    fun `test createTransaction endpoint`() {
        val owner = UserBuilder()
            .withId(1L)
            .build()
        val userId = owner.id!!
        val dollarResponse = DolarPriceDTO("USD","cripto","Cripto","1111.5","1116.13", "2024-11-26T11:58:00.000Z")
        val transactionDTO = TransactionIntentDTO(
            price = 50000.0f,
            cryptoActive = CryptoActive.ADAUSDT,
            amount = 0.01f,
            operationType = OperationType.PURCHASE
        )

        val transactionIntent = TransactionIntentBuilder()
            .withId(1L)
            .withOwner(owner)
            .withPrice(50000.0f)
            .withAmount(0.01f)
            .withOperationType(OperationType.PURCHASE)
            .withStatus(TradeStatus.PENDING)
            .withCreatedDate(LocalDateTime.now())
            .withCryptoActive(CryptoActive.ADAUSDT)
            .build()

        `when`(
            transactionIntentService.createTransactionIntent(
                MockitoHelper.anyObject(),
                eq(userId)
            )
        ).thenReturn(
            transactionIntent
        )
        `when`(dollarService.getDolarCryptoPrice()).thenReturn(dollarResponse)

        mockMvc.perform(
            post("/create/$userId")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapper().writeValueAsString(transactionDTO))
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.cryptoActive").value(transactionIntent.cryptoActive.toString()))
            .andExpect(jsonPath("$.amount").value(transactionIntent.amount))
            .andExpect(jsonPath("$.price").value(transactionIntent.price))
            .andExpect(jsonPath("$.operationType").value(transactionIntent.operationType.toString()))
    }
}
