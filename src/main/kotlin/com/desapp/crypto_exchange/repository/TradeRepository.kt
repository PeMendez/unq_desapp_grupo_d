package com.desapp.crypto_exchange.repository

import com.desapp.crypto_exchange.model.Trade
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TradeRepository : JpaRepository<Trade, Long> {

}