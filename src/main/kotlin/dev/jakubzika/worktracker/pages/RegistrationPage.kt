package dev.jakubzika.worktracker.pages

import dev.jakubzika.worktracker.auth.AuthService
import dev.jakubzika.worktracker.db.Schema
import dev.jakubzika.worktracker.repository.UserRepository
import dev.jakubzika.worktracker.routing.Endpoint
import dev.jakubzika.worktracker.routing.FORM_FIELD_NAME
import dev.jakubzika.worktracker.routing.FORM_FIELD_PASSWD
import dev.jakubzika.worktracker.routing.FORM_FIELD_PASSWD_AGAIN
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*
import org.koin.ktor.ext.inject

fun Route.registrationPage(route: String) {

    val userRepository : UserRepository by inject()

    route(route) {
        get {
            call.respondHtml {
                head {
                    title { +"Ktor: Registration Page" }
                }
                body {
                    registrationForm()
                }
            }
        }
        post {
            val parameters = call.receiveParameters()

            val username = parameters[FORM_FIELD_NAME]
                    ?: throw IllegalArgumentException("Missing parameter: username")
            val passwd = parameters[FORM_FIELD_PASSWD]
                    ?: throw IllegalArgumentException("Missing parameter: password")
            val passwdAgain = parameters[FORM_FIELD_PASSWD_AGAIN]
                    ?: throw IllegalArgumentException("Missing parameter: password")

            val user: Schema.User? = userRepository.findUser(username)

            if (passwd == passwdAgain) {
                call.respondText("Password did not matched.", contentType = ContentType.Text.Plain)
            } else if (user == null) {
                call.respondText("This username is already used.", contentType = ContentType.Text.Plain)
            } else {
                if (AuthService.register(username, passwd)) {
                    call.respondText("Registration success.", contentType = ContentType.Text.Plain)
                } else {
                    call.respondText("Registration failed.", contentType = ContentType.Text.Plain)
                }
            }
        }
    }
}

fun FlowContent.registrationForm() {
    form(
            action = Endpoint.REGISTRATION.url,
            encType = FormEncType.applicationXWwwFormUrlEncoded,
            method = FormMethod.post
    ) {
        p {
            +"User name: "
            textInput(name = FORM_FIELD_NAME)
        }
        p {
            +"Password: "
            passwordInput(name = FORM_FIELD_PASSWD)
        }
        p {
            +"Password again: "
            passwordInput(name = FORM_FIELD_PASSWD_AGAIN)
        }
        p {
            submitInput { value = "Login" }
        }
    }
}