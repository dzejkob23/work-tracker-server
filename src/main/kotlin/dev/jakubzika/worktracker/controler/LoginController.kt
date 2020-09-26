package dev.jakubzika.worktracker.controler

import dev.jakubzika.worktracker.auth.Security
import dev.jakubzika.worktracker.repository.UserRepository

interface LoginController {

    suspend fun authenticate(userName: String, password: String): Security.UserLoginPrincipal?

}

class LoginControllerImpl(private val userRepository: UserRepository) : LoginController {

    override suspend fun authenticate(userName: String, password: String): Security.UserLoginPrincipal? {

        val user = userRepository.findUser(userName) ?: return null
        val hash = Security.encryptPBKDF2(password, user.salt)

        return if (user.passwd.contentEquals(hash.first)) {
            Security.UserLoginPrincipal(userName = user.nickname, userId = user.id!!)
        } else {
            null
        }
    }

}