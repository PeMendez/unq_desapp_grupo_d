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
    var owner: User? = null

    @ManyToOne
    var price: Price? = null

    var amount: Float? = null
    var operationType: OperationType? = null
    var active: Boolean? = null
    var createdDate: LocalDateTime? = LocalDateTime.now()
    var status: TransactionStatus? = null
    var priceInArs : Float? = null

    constructor(
        owner : User?,
        price : Price?,
        amount : Float?,
        operationType: OperationType?,
        active: Boolean? = true,
        status: TransactionStatus = TransactionStatus.OPEN
    ): this() {
        this.owner = owner
        this.price = price
        this.amount = amount
        this.operationType = operationType
        this.active = active
        this.status = status
    }

    fun calculateArsAmount() {
        TODO("Implement this function in the future")
    }

    fun validatePrice(intentPrice: Float, currentPrice: Float) {
        val lowerBound = currentPrice * 0.95f
        val upperBound = currentPrice * 1.05f
        if (intentPrice !in lowerBound..upperBound) {
            throw PriceOutOfRangeException("El precio de la intención no está dentro del margen de +/- 5% del precio actual.")
        }
    }

}