package dev.jakubzika.worktracker.auth

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.sessions.*
import io.ktor.util.*
import io.ktor.util.pipeline.*
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

object Security {

    @OptIn(InternalAPI::class)
    fun encryptPBKDF2(password: String, salt: ByteArray? = null): Pair<ByteArray, ByteArray> {

        val usedSalt: ByteArray = if (salt == null) {
            val random = SecureRandom()
            val tmpSalt = ByteArray(256)
            random.nextBytes(tmpSalt)
            tmpSalt
        } else { salt }

        val pbKeySpec = PBEKeySpec(password.toCharArray(), usedSalt, 65536, 256)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")

        return Pair(secretKeyFactory.generateSecret(pbKeySpec).encoded, usedSalt)
    }

    data class UserLoginPrincipal(
            val userName: String,
            val userId: Long
    ) : Principal
}

fun PipelineContext<Unit, ApplicationCall>.isUserAuthorized(): Boolean
        = call.sessions.get<SessionLogin>() != null

fun PipelineContext<Unit, ApplicationCall>.logoutUser()
        = call.sessions.clear<SessionLogin>()