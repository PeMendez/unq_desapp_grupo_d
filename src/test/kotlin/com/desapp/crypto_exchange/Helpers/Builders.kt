package com.desapp.crypto_exchange.Helpers

import com.desapp.crypto_exchange.model.*
import java.time.LocalDateTime

/*class TransactionIntentBuilder {
    private var owner: User? = null
    private var price: Price? = null
    private var amount: Float? = null
    private var operationType: OperationType? = null
    private var active: Boolean? = true
    private var createdDate: LocalDateTime? = LocalDateTime.now()

    fun withOwner(owner: User) = apply { this.owner = owner }
    fun withPrice(price: Price) = apply { this.price = price }
    fun withAmount(amount: Float) = apply { this.amount = amount }
    fun withOperationType(operationType: OperationType) = apply { this.operationType = operationType }
    fun withActive(active: Boolean) = apply { this.active = active }
    fun withCreatedDate(createdDate: LocalDateTime) = apply { this.createdDate = createdDate }

    fun build(): TransactionIntent {
        return TransactionIntent(
            owner = owner,
            price = price,
            amount = amount,
            operationType = operationType,
            active = active
        ).apply {
            this.createdDate = this@TransactionIntentBuilder.createdDate
        }
    }
}*/

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
