package com.desapp.crypto_exchange.model

import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
@Entity
class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    var buyer: User? = null

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    var seller: User? = null

    @ManyToOne
    @JoinColumn(name = "transaction_intent_id", nullable = false)
    var transactionIntent: TransactionIntent? = null

    var cryptoActive: CryptoActive? = null
    var nominalAmount: Float? = null
    var tradePrice: Float? = null
    var amountInArs: Float? = null
    var createdDate: LocalDateTime? = LocalDateTime.now()
    var tradeStatus: TradeStatus = TradeStatus.PENDING

    @Enumerated(EnumType.STRING)
    var tradeType: OperationType? = null

    @Column(nullable = true)
    var reputationPoints: Int? = null

    constructor(
        buyer: User,
        seller: User,
        transactionIntent: TransactionIntent,
        cryptoActive: CryptoActive,
        nominalAmount: Float,
        tradePrice: Float,
        tradeType: OperationType,
        tradeStatus: TradeStatus
    ) {
        this.buyer = buyer
        this.seller = seller
        this.transactionIntent = transactionIntent
        this.cryptoActive = cryptoActive
        this.nominalAmount = nominalAmount
        this.tradePrice = tradePrice
        this.tradeType = tradeType
        this.amountInArs = tradePrice * nominalAmount
        this.tradeStatus = tradeStatus
    }
}
