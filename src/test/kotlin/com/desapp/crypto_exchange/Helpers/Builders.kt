package com.desapp.crypto_exchange.Helpers

import com.desapp.crypto_exchange.model.*
import java.time.LocalDateTime

class TransactionIntentBuilder {
    private var id: Long? = null
    private var owner: User? = null
    private var price: Float? = 8F
    private var cryptoActive: CryptoActive? = CryptoActive.ADAUSDT
    private var status : TradeStatus = TradeStatus.PENDING
    private var amount: Float = 1F
    private var operationType: OperationType? = OperationType.SALE
    private var active: Boolean = true
    private var createdDate: LocalDateTime? = LocalDateTime.now()

    fun build(): TransactionIntent {
        var transactionIntent = TransactionIntent(owner, price, amount, operationType, active, status, cryptoActive)
        transactionIntent.createdDate = createdDate
        transactionIntent.id = id
        return transactionIntent
    }

    fun withOwner(owner: User?) : TransactionIntentBuilder {
        this.owner = owner
        return this
    }
    fun withId(id: Long?): TransactionIntentBuilder{
        this.id = id
        return this
    }

    fun withCryptoActive(cryptoActive: CryptoActive?): TransactionIntentBuilder {
        this.cryptoActive = cryptoActive
        return this
    }

    fun withAmount(amount: Float): TransactionIntentBuilder {
        this.amount = amount
        return this
    }

    fun withPrice(price: Float?): TransactionIntentBuilder {
        this.price = price
        return this
    }

    fun withOperationType(operationType: OperationType): TransactionIntentBuilder {
        this.operationType = operationType
        return this
    }

    fun withCreatedDate(createdDate: LocalDateTime): TransactionIntentBuilder {
        this.createdDate = createdDate
        return this
    }

    fun withStatus(status: TradeStatus): TransactionIntentBuilder {
        this.status = status
        return this
    }

    fun withActive(active: Boolean): TransactionIntentBuilder{
        this.active = active
        return this
    }


}

class UserBuilder {
    private var id: Long? = null
    private var firstName: String? = "a valid name"
    private var lastName: String? = "a valid last name"
    private var email: String? = "example@email.com"
    private var password: String? = "ValidPassword1!"
    private var address: String? = "a valid adress"
    private var cvu: String? = "1234567891234567891234"
    private var cryptoWalletAddress: String? = "12345678"

    fun build(): User {
        val user = User(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password,
            address = address,
            cvu = cvu,
            cryptoWalletAddress = cryptoWalletAddress
        )

        if (this.id != null) {
            user.id = this.id
        }
        return user
    }

    fun withId(id: Long?): UserBuilder {
        this.id = id
        return this
    }

    fun withFirstName(firstName: String?): UserBuilder {
        this.firstName = firstName
        return this
    }

    fun withLastName(lastName: String?): UserBuilder {
        this.lastName = lastName
        return this
    }

    fun withEmail(email: String?): UserBuilder {
        this.email = email
        return this
    }

    fun withPassword(password: String?): UserBuilder {
        this.password = password
        return this
    }

    fun withAddress(address: String?): UserBuilder {
        this.address = address
        return this
    }

    fun withCVU(cvu: String?): UserBuilder {
        this.cvu = cvu
        return this
    }

    fun withCryptoWalletAddress(cryptoWalletAddress: String?): UserBuilder {
        this.cryptoWalletAddress = cryptoWalletAddress
        return this
    }
}

class PriceBuilder {

    private var id: Long? = null
    private var cryptoActive: CryptoActive? = CryptoActive.ADAUSDT
    private var dateTime: LocalDateTime? = LocalDateTime.now()
    private var price: Float? = 10F

    fun build(): Price {
        val priceInstance = Price(
            cryptoActive = cryptoActive,
            price = price
        )
        if (this.id != null) {
            priceInstance.id = this.id
        }
        priceInstance.dateTime = this.dateTime

        return priceInstance
    }

    fun withId(id: Long?): PriceBuilder {
        this.id = id
        return this
    }

    fun withCryptoActive(cryptoActive: CryptoActive?): PriceBuilder {
        this.cryptoActive = cryptoActive
        return this
    }

    fun withDateTime(dateTime: LocalDateTime?): PriceBuilder {
        this.dateTime = dateTime
        return this
    }

    fun withPrice(price: Float?): PriceBuilder {
        this.price = price
        return this
    }
}

class TradeBuilder {
    private var id: Long? = null
    private var buyer: User? = null
    private var seller: User? = null
    private var transactionIntent: TransactionIntent? = null
    private var cryptoActive: CryptoActive? = null
    private var nominalAmount: Float = 0.0f
    private var tradePrice: Float = 0.0f
    private var amountInArs: Float? = null
    private var tradeStatus: TradeStatus = TradeStatus.PENDING
    private var tradeType: OperationType? = null
    private var reputationPoints: Int? = null
    private var createdDate: LocalDateTime = LocalDateTime.now()

    // Métodos de configuración

    fun withId(id: Long?): TradeBuilder {
        this.id = id
        return this
    }

    fun withBuyer(buyer: User): TradeBuilder {
        this.buyer = buyer
        return this
    }

    fun withSeller(seller: User): TradeBuilder {
        this.seller = seller
        return this
    }

    fun withTransactionIntent(transactionIntent: TransactionIntent): TradeBuilder {
        this.transactionIntent = transactionIntent
        return this
    }

    fun withCryptoActive(cryptoActive: CryptoActive): TradeBuilder {
        this.cryptoActive = cryptoActive
        return this
    }

    fun withNominalAmount(nominalAmount: Float): TradeBuilder {
        this.nominalAmount = nominalAmount
        return this
    }

    fun withAmountInArs(arsAmount: Float): TradeBuilder {
        this.amountInArs = arsAmount
        return this
    }

    fun withTradePrice(tradePrice: Float): TradeBuilder {
        this.tradePrice = tradePrice
        return this
    }

    fun withTradeStatus(tradeStatus: TradeStatus): TradeBuilder {
        this.tradeStatus = tradeStatus
        return this
    }

    fun withTradeType(tradeType: OperationType): TradeBuilder {
        this.tradeType = tradeType
        return this
    }

    fun withReputationPoints(reputationPoints: Int?): TradeBuilder {
        this.reputationPoints = reputationPoints
        return this
    }

    fun withCreatedDate(createdDate: LocalDateTime): TradeBuilder {
        this.createdDate = createdDate
        return this
    }

    // Método de construcción del Trade
    fun build(): Trade {
        // Validación de campos obligatorios
        if (buyer == null || seller == null || transactionIntent == null || cryptoActive == null || tradeType == null) {
            throw IllegalStateException("Buyer, Seller, TransactionIntent, CryptoActive and TradeType must be provided")
        }

        // Calcular amountInArs si no está presente
        if (amountInArs == null) {
            amountInArs = tradePrice * nominalAmount
        }

        // Construir la instancia de Trade
        val trade = Trade(
            buyer = this.buyer!!,
            seller = this.seller!!,
            transactionIntent = this.transactionIntent!!,
            cryptoActive = this.cryptoActive!!,
            nominalAmount = this.nominalAmount,
            tradePrice = this.tradePrice,
            tradeType = this.tradeType!!,
            tradeStatus = this.tradeStatus
        )

        if (this.id != null) {
            trade.id = this.id
        }

        return trade
    }
}
