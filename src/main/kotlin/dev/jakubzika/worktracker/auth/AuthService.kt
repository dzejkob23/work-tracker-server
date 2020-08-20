package dev.jakubzika.worktracker.auth

import io.ktor.auth.*

object AuthService {

    fun authenticate(username: String, passwd: String): Principal? {
        // TODO
        return UserIdPrincipal(username)
    }

    fun register(username: String?, passwd: String?): Boolean {
        // TODO
        // val hashedPasswd = passwd.encode()
        // val registrationSuccess = db.registerUserToDB
        return false
    }
}