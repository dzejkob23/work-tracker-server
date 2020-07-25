package dev.jakubzika.worktracker.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import dev.jakubzika.worktracker.db.Schema.User
import io.ktor.application.Application
import java.util.*

class JwtService {

    private val jwtSecret = System.getenv("JWT_SECRET")
    private val algorithm = Algorithm.HMAC512(jwtSecret)

    fun makeJwtVerifier(issuer: String, audience: String): JWTVerifier = JWT
            .require(algorithm)
            .withAudience(audience)
            .withIssuer(issuer)
            .build()

//    fun generateToken(user: User): String = JWT.create()
//            .withSubject("Authentication")
//            .withIssuer(issuer)
//            .withClaim("id", user.id)
//            .withExpiresAt(expiresAt())
//            .sign(algorithm)

//    private fun expiresAt() =
//            Date(System.currentTimeMillis() + 3_600_000 * 24) // 24 hours
}