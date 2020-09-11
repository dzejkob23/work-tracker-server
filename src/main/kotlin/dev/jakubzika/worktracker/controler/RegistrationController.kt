package dev.jakubzika.worktracker.controler

import dev.jakubzika.worktracker.auth.AuthService
import dev.jakubzika.worktracker.db.Schema
import dev.jakubzika.worktracker.repository.UserRepository

interface RegistrationController {

    suspend fun validateAndRegisterUser(
            userName: String?,
            password: String?,
            passwordAgain: String?
    )

}

class RegistrationControllerImpl(private val userRepository: UserRepository) : RegistrationController {

    override suspend fun validateAndRegisterUser(userName: String?, password: String?, passwordAgain: String?) {
        userName ?: throw IllegalArgumentException("Missing parameter: username")
        password ?: throw IllegalArgumentException("Missing parameter: password")
        passwordAgain ?: throw IllegalArgumentException("Missing parameter: password")

        val user: Schema.User? = userRepository.findUser(userName)

        if (password != passwordAgain) throw IllegalArgumentException("Password did not matched.")
        if (user != null) throw IllegalArgumentException("This username is already used.")

        val hashedPasswd = AuthService.encryptPBKDF2(password)

        userRepository.addUser(userName, hashedPasswd.toString())
    }
}