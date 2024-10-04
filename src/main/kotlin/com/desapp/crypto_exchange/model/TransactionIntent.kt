package com.desapp.crypto_exchange.model

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

    var amount : Float? = null
    var operationType: OperationType? = null
    var active: Boolean? = null
    var createdDate: LocalDateTime? = LocalDateTime.now()

    constructor(
        owner : User?,
        price : Price?,
        amount : Float?,
        operationType: OperationType?,
        active: Boolean? = true
    ): this() {
        this.owner = owner
        this.price = price
        this.amount = amount
        this.operationType = operationType
        this.active = active
    }

    fun calculateArsAmount() {

    }

}