package com.desapp.crypto_exchange.Exceptions

    class UsernameAlreadyTakenException(override val message: String): Exception(message)
    class UserCannotBeRegisteredException(override val message: String): Exception(message)
    class UserNotRegisteredException(override val message: String): Exception(message)
    class  InvalidLoginException(override val message: String): Exception(message)