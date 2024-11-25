package com.desapp.crypto_exchange.model

import com.desapp.crypto_exchange.utils.PriceOutOfRangeException
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class TransactionIntent() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "owner_id")
    var owner: User? = null

    var price: Float? = null
    var amount: Float? = null
    var operationType: OperationType? = null
    var active: Boolean? = null
    var createdDate: LocalDateTime? = LocalDateTime.now()
    var status: TradeStatus? = null
    var priceInArs : Float? = null
    var cryptoActive : CryptoActive? = null

    constructor(
        owner : User?,
        price : Float?,
        amount : Float?,
        operationType: OperationType?,
        active: Boolean? = true,
        status: TradeStatus = TradeStatus.PENDING,
        cryptoActive: CryptoActive
    ): this() {
        this.owner = owner
        this.price = price
        this.amount = amount
        this.operationType = operationType
        this.active = active
        this.status = status
        this.cryptoActive = cryptoActive
    }

    fun calculateArsAmount(dollarPrice: Float) {
        if (price != null) {
            priceInArs = price!! * dollarPrice
        } else {
            throw IllegalArgumentException("Price must be specified to calculate ARS amount.")
        }
    }

    fun validatePrice(intentPrice: Float, currentPrice: Float) {
        val lowerBound = currentPrice * 0.95f
        val upperBound = currentPrice * 1.05f
        if (intentPrice !in lowerBound..upperBound) {
            throw PriceOutOfRangeException("El precio de la intención no está dentro del margen de +/- 5% del precio actual.")
        }
    }

}