package dev.jakubzika.worktracker.page

import dev.jakubzika.worktracker.auth.SessionLogin
import dev.jakubzika.worktracker.auth.isUserAuthorized
import dev.jakubzika.worktracker.controler.LoginController
import dev.jakubzika.worktracker.page.template.MainTemplate
import dev.jakubzika.worktracker.routing.Endpoint
import dev.jakubzika.worktracker.routing.FORM_FIELD_NAME
import dev.jakubzika.worktracker.routing.FORM_FIELD_PASSWD
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import kotlinx.html.*
import org.koin.ktor.ext.inject

fun Routing.loginPage(endpoint: Endpoint = Endpoint.LOGIN) {

    val loginController: LoginController by inject()

    route(endpoint.url) {
        get {
            if (isUserAuthorized()) {
                call.respondRedirect(Endpoint.HOME.url)
            } else {
                call.respondHtml {
                    insert(MainTemplate()) {
                        content {
                            h1 { + "Login page" }
                            loginForm(endpoint)
                        }
                    }
                }
            }
        }
        post {
            val parameters = call.receiveParameters()
            val principal = loginController.authenticate(
                    parameters[FORM_FIELD_NAME] ?: "",
                    parameters[FORM_FIELD_PASSWD] ?: ""
            )
            if (principal != null) {
                call.sessions.set(
                        SessionLogin(
                                userId = principal.userId,
                                userName = principal.userName
                        )
                )
                call.respondRedirect(Endpoint.HOME.url)
            } else {
                call.respondRedirect(endpoint.url)
            }
        }
    }
}

private fun FlowContent.loginForm(endpoint: Endpoint) {
    form(
            action = endpoint.url,
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
            submitInput { value = "Login" }
        }
    }
}