package dev.jakubzika.worktracker.auth

import io.ktor.auth.*
import io.ktor.util.*
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object AuthService {

    @OptIn(InternalAPI::class)
    fun encryptPBKDF2(password: String): ByteArray {

        val random = SecureRandom()
        val salt = ByteArray(256)
        random.nextBytes(salt)

        val pbKeySpec = PBEKeySpec(password.toCharArray(), salt, 65536, 256)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")

        return secretKeyFactory.generateSecret(pbKeySpec).encoded
    }

    fun authenticate(username: String, passwd: String): Principal? {
        // TODO
        return UserIdPrincipal(username)
    }
}