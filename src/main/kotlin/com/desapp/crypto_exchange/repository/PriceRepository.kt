package com.desapp.crypto_exchange.repository

import com.desapp.crypto_exchange.model.CryptoActive
import com.desapp.crypto_exchange.model.Price
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate


@Repository
interface PriceRepository : JpaRepository<Price, Long> {
    fun findByCryptoActive(cryptoActive: CryptoActive): Price?
    fun findAllByCryptoActive(crypto: CryptoActive) : List<Price>
    fun findByCryptoActiveAndDateTimeAfter(cryptoActive: CryptoActive, dateTime: LocalDate = LocalDate.now().minusDays(1)): List<Price>
    fun findFirstByCryptoActiveOrderByPriceDesc(cryptoActive: CryptoActive): Price?


}
