package com.desapp.crypto_exchange.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Price() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var cryptoActive : CryptoActive? = null
    var dateTime : LocalDateTime? = LocalDateTime.now()
    var price : Float? = null

    constructor(
        cryptoActive : CryptoActive?,
        price : Float?
    ): this() {
        this.cryptoActive = cryptoActive
        this.price = price
        this.dateTime = LocalDateTime.now()
    }

}

