package com.desapp.crypto_exchange.controller

import com.desapp.crypto_exchange.Helpers.TradeBuilder
import com.desapp.crypto_exchange.Helpers.TransactionIntentBuilder
import com.desapp.crypto_exchange.Helpers.UserBuilder
import com.desapp.crypto_exchange.model.CryptoActive
import com.desapp.crypto_exchange.model.OperationType
import com.desapp.crypto_exchange.model.TradeStatus
import com.desapp.crypto_exchange.service.TradeService
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.mockito.Mockito.verify
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print

@SpringBootTest
@AutoConfigureMockMvc
class TradeControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var tradeService: TradeService

    @Test
    @WithMockUser
    fun `test createTrade endpoint`() {
        val buyerId = 1L
        val transactionIntentId = 2L

        val transactionIntent = TransactionIntentBuilder()
            .withId(transactionIntentId)
            .build()

        val trade = TradeBuilder()
            .withId(1L)
            .withBuyer(UserBuilder().withId(buyerId).build())
            .withSeller(UserBuilder().withId(3L).build())
            .withCryptoActive(CryptoActive.BNBUSDT)
            .withNominalAmount(0.5f)
            .withTradePrice(50000f)
            .withAmountInArs(25000f)
            .withTradeStatus(TradeStatus.PENDING)
            .withTradeType(OperationType.PURCHASE) // Este ya está bien
            .withTransactionIntent(transactionIntent) // Asegúrate de pasar el TransactionIntent
            .build()

        `when`(tradeService.createTrade(buyerId, transactionIntentId)).thenReturn(trade)

        mockMvc.perform(
            post("/trades/create/$buyerId/$transactionIntentId")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(trade.id))
            .andExpect(jsonPath("$.buyer.id").value(trade.buyer!!.id))
            .andExpect(jsonPath("$.seller.id").value(trade.seller!!.id))
            .andExpect(jsonPath("$.cryptoActive").value(trade.cryptoActive.toString()))
            .andExpect(jsonPath("$.nominalAmount").value(trade.nominalAmount))
            .andExpect(jsonPath("$.tradePrice").value(trade.tradePrice))
            .andExpect(jsonPath("$.amountInArs").value(trade.amountInArs))
    }

    @Test
    @WithMockUser
    fun `test completeTrade endpoint`() {
        val tradeId = 1L

        mockMvc.perform(
            put("/trades/complete/$tradeId")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk)

        verify(tradeService).completeTrade(tradeId)
    }

    @Test
    @WithMockUser
    fun `test cancelTrade endpoint`() {
        val tradeId = 1L

        mockMvc.perform(
            put("/trades/cancel/$tradeId")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andDo(print())
            .andExpect(status().isOk)

        verify(tradeService).cancelTrade(tradeId)
    }

    @Test
    @WithMockUser
    fun `test getAllTrades endpoint`() {
        val buyerId = 1L
        val transactionIntentId = 2L

        val transactionIntent = TransactionIntentBuilder()
            .withId(transactionIntentId)
            .build()

        val trade = TradeBuilder()
            .withId(1L)
            .withBuyer(UserBuilder().withId(buyerId).build())
            .withSeller(UserBuilder().withId(3L).build())
            .withCryptoActive(CryptoActive.BNBUSDT)
            .withNominalAmount(0.5f)
            .withTradePrice(50000f)
            .withAmountInArs(25000f)
            .withTradeStatus(TradeStatus.PENDING)
            .withTradeType(OperationType.PURCHASE) // Este ya está bien
            .withTransactionIntent(transactionIntent) // Asegúrate de pasar el TransactionIntent
            .build()
        `when`(tradeService.getAllTrades()).thenReturn(listOf(trade))

        mockMvc.perform(get("/trades"))
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(1))
            .andExpect(jsonPath("$[0].id").value(trade.id))
            .andExpect(jsonPath("$[0].buyer.id").value(trade.buyer!!.id))
            .andExpect(jsonPath("$[0].cryptoActive").value(trade.cryptoActive.toString()))
    }
}
