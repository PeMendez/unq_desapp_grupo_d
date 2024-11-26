package com.desapp.crypto_exchange.model
import com.desapp.crypto_exchange.Exceptions.*

object Check {

    fun validate(value: String?, pattern: Regex, errorMsg: String): String{
        checkNull(value, errorMsg)
        if (!pattern.matches(value!!)) throw UserCannotBeRegisteredException(errorMsg)
        return value
    }
    fun validateRange(value:String?, min: Int, max: Int, errorMsg: String): String{
        checkNull(value, errorMsg)
        if(value!!.length !in min..max) throw UserCannotBeRegisteredException(errorMsg)
        return value
    }

    private fun checkNull(value: String?, errorMsg: String) {
        if(value == null) throw UserCannotBeRegisteredException(errorMsg)
    }

}