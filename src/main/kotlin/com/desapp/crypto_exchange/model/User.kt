package com.desapp.crypto_exchange.model

import jakarta.persistence.*

@Entity
@Table(name =  "`user`")
class User(){
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false, unique =true)
    var email: String? = null
    var cvu: String? = null
    var cryptoWalletAddress: String? = null

    @Column(nullable = false, unique =false)
    var firstName: String? = null
    var lastName: String? = null
    var address: String? = null
    var password: String? = null

    constructor(firstName: String?, lastName: String?, email: String?, password: String?, address: String?, cvu: String?,cryptoWalletAddress: String?): this() {
        val rangeErrorMsg: (property: String) -> String = { property -> "The $property is too short or too long" }
        val emailPattern = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
        val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_]).{6,}\$")
        val cvuPattern = Regex("^\\d{22}$")
        val cryptoWalletAddressPattern = Regex("^\\d{8}$")


        this.firstName = Check.validateRange(firstName, 3, 30, rangeErrorMsg(User::firstName.name))
        this.lastName = Check.validateRange(lastName, 3, 30, rangeErrorMsg(User::lastName.name))
        this.email = Check.validate(email, emailPattern, "The email does not have a valid format")
        this.address = Check.validateRange(address, 10, 30, rangeErrorMsg(User::address.name))
        this.password = Check.validate(password, passwordPattern, "The password is too weak")
        this.cvu = Check.validate(cvu, cvuPattern, "The cvu must have 22 digits")
        this.cryptoWalletAddress =
            Check.validate(cryptoWalletAddress, cryptoWalletAddressPattern, "The crypto wallet address must have 8 digits")
    }

    }

