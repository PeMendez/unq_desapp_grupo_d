package com.desapp.crypto_exchange.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class TransactionIntent() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var amount : Float? = null
    var operationType: OperationType? = null
    var active: Boolean? = null
    var createdDate: LocalDateTime? = LocalDateTime.now()
    var argsAmount : Float? = null
    var price : String? = null

    constructor(
        amount : Float?,
        operationType: OperationType?,
        active: Boolean?,
        argsAmount : Float?

    ): this() {
        this.amount = amount
        this.operationType = operationType
        this.active = active



    }

}