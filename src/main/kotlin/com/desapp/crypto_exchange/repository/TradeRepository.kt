package com.desapp.crypto_exchange.repository

import com.desapp.crypto_exchange.model.Trade
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface TradeRepository: JpaRepository<Trade, Long> {
}
