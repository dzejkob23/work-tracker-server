package dev.jakubzika.worktracker.controler

import dev.jakubzika.worktracker.auth.AuthService
import dev.jakubzika.worktracker.repository.UserRepository
import io.ktor.auth.*

interface LoginController {

    suspend fun authenticate(userName: String, password: String): Principal?

}

class LoginControllerImpl(private val userRepository: UserRepository) : LoginController {

    override suspend fun authenticate(userName: String, password: String): Principal? {

        val user = userRepository.findUser(userName) ?: return null
        val hash = AuthService.encryptPBKDF2(password, user.salt)

        return if (user.passwd.contentEquals(hash.first)) {
            AuthService.UserLoginPrincipal(userName = user.nickname, userId = user.id!!)
        } else {
            null
        }
    }

}