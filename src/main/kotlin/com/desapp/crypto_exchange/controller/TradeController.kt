package com.desapp.crypto_exchange.controller

import com.desapp.crypto_exchange.DTOs.TradeDTO
import com.desapp.crypto_exchange.Mapper.TradeMapper
import com.desapp.crypto_exchange.service.TradeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/trades")
class TradeController {

    @Autowired
    lateinit var tradeService: TradeService

    @PostMapping("/create/{buyerId}/{transactionIntentId}")
    fun createTrade(
        @PathVariable buyerId: Long,
        @PathVariable transactionIntentId: Long
    ): TradeDTO {
        val trade = tradeService.createTrade(buyerId, transactionIntentId)
        return TradeMapper.toDTO(trade)
    }

    @PutMapping("/complete/{tradeId}")
    fun completeTrade(@PathVariable tradeId: Long) {
        tradeService.completeTrade(tradeId)
    }

    @PutMapping("/cancel/{tradeId}")
    fun cancelTrade(@PathVariable tradeId: Long) {
        tradeService.cancelTrade(tradeId)
    }

    @GetMapping
    fun getAllTrades(): List<TradeDTO> {
        val trades = tradeService.getAllTrades()
        return trades.map { TradeMapper.toDTO(it) }
    }

}
