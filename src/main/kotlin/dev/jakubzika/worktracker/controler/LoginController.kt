package dev.jakubzika.worktracker.controler

import dev.jakubzika.worktracker.auth.AuthService
import dev.jakubzika.worktracker.repository.UserRepository
import io.ktor.auth.*

interface LoginController {

    suspend fun authenticate(userName: String, password: String): Principal?

}

class LoginControllerImpl(private val userRepository: UserRepository) : LoginController {

    override suspend fun authenticate(userName: String, password: String): Principal? {

        val hashedPassword = AuthService.encryptPBKDF2(password)
        val user = userRepository.findUser(userName)

        return when {
            (user == null) -> null
            (user.passwd.contentEquals(hashedPassword)) -> UserIdPrincipal(userName)
            else -> null
        }
    }

}