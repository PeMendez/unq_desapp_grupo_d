package com.desapp.crypto_exchange.service
import com.desapp.crypto_exchange.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService {

    val secretKey = "7bace0d50a22258fea65936973a3a4e139e6784c406e9192004169f037e98d15"
    fun createToken(user: User): String {
        return Jwts.builder()
            .subject(user.email)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 24*60*60*1000))
            .signWith(signingKey())
            .compact()
    }
    fun getClaimsFromToken(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(signingKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }
    fun <T> resolveClaim(token: String, resolver: (Claims) -> T): T {
        val claims = getClaimsFromToken(token)
        return resolver(claims)
    }
    fun getUsernameFromToken(token: String): String {
        return resolveClaim(token) { it.subject }
    }
    private fun signingKey(): SecretKey {
        val keyBytes = Decoders.BASE64URL.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val usernameFromToken = getUsernameFromToken(token)
        return usernameFromToken == userDetails.username && !isTokenExpired(token)
    }
    private fun isTokenExpired(token: String): Boolean {
        return getExpirationDate(token).before(Date())
    }
    private fun getExpirationDate(token: String): Date {
        return resolveClaim(token) { it.expiration }
    }
}
