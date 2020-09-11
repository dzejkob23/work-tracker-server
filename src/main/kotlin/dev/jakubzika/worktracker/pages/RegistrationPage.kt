package dev.jakubzika.worktracker.pages

import dev.jakubzika.worktracker.controler.RegistrationController
import dev.jakubzika.worktracker.routing.Endpoint
import dev.jakubzika.worktracker.routing.FORM_FIELD_NAME
import dev.jakubzika.worktracker.routing.FORM_FIELD_PASSWD
import dev.jakubzika.worktracker.routing.FORM_FIELD_PASSWD_AGAIN
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.request.*
import io.ktor.routing.*
import kotlinx.html.*
import org.koin.ktor.ext.inject

fun Route.registrationPage(route: String) {

    val registrationController : RegistrationController by inject()

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
            registrationController.validateAndRegisterUser(
                    parameters[FORM_FIELD_NAME],
                    parameters[FORM_FIELD_PASSWD],
                    parameters[FORM_FIELD_PASSWD_AGAIN]
            )
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