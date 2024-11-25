package com.desapp.crypto_exchange.service

import com.desapp.crypto_exchange.model.Trade

interface TradeService {
    fun createTrade(buyerId: Long, transactionIntentId: Long): Trade
    fun completeTrade(tradeId: Long)
    fun cancelTrade(tradeId: Long)
    fun getAllTrades(): List<Trade>
}