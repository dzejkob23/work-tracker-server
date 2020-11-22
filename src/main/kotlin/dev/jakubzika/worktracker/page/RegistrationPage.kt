package dev.jakubzika.worktracker.page

import dev.jakubzika.worktracker.controler.RegistrationController
import dev.jakubzika.worktracker.extension.translationId
import dev.jakubzika.worktracker.page.template.MainTemplate
import dev.jakubzika.worktracker.routing.Endpoint
import dev.jakubzika.worktracker.routing.FORM_FIELD_NAME
import dev.jakubzika.worktracker.routing.FORM_FIELD_PASSWD
import dev.jakubzika.worktracker.routing.FORM_FIELD_PASSWD_AGAIN
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*
import org.koin.ktor.ext.inject

fun Route.registrationPage(endpoint: Endpoint = Endpoint.REGISTRATION) {

    val registrationController : RegistrationController by inject()

    route(endpoint.url) {
        get {
            call.respondHtmlTemplate(registrationView(endpoint = endpoint)) {}
        }
        post {
            val parameters = call.receiveParameters()
            val user = registrationController.validateAndRegisterUser(
                    parameters[FORM_FIELD_NAME],
                    parameters[FORM_FIELD_PASSWD],
                    parameters[FORM_FIELD_PASSWD_AGAIN]
            )
            call.respond(user?.toString() ?: "Something is wrong.")
        }
    }
}

private fun registrationView(main: MainTemplate = MainTemplate(), endpoint: Endpoint) = object : Template<HTML> {
    override fun HTML.apply() {
        insert(main) {
            content {
                div(classes = "flex-vertical") {
                    h1 { translationId("registration-page-title") }
                    form(
                            action = endpoint.url,
                            encType = FormEncType.applicationXWwwFormUrlEncoded,
                            method = FormMethod.post
                    ) {
                        p {
                            span { translationId("registration-form-username") }
                            textInput(name = FORM_FIELD_NAME)
                        }
                        p {
                            span { translationId("registration-form-password") }
                            passwordInput(name = FORM_FIELD_PASSWD)
                        }
                        p {
                            span { translationId("registration-form-password-again") }
                            passwordInput(name = FORM_FIELD_PASSWD_AGAIN)
                        }
                        p {
                            submitInput(classes = "buttonPrimary") {
                                translationId("registration-form-button")
                            }
                        }
                    }
                }
            }
        }
    }
}