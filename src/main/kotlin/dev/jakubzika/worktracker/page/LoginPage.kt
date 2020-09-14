package dev.jakubzika.worktracker.page

import dev.jakubzika.worktracker.AUTH_USER
import dev.jakubzika.worktracker.auth.AuthService
import dev.jakubzika.worktracker.auth.SessionLogin
import dev.jakubzika.worktracker.page.template.DoneTemplate
import dev.jakubzika.worktracker.routing.Endpoint
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.html.*
import io.ktor.routing.*
import io.ktor.sessions.*

fun Routing.loginPage(endpoint: Endpoint = Endpoint.LOGIN) {

    authenticate(AUTH_USER) {
        get(endpoint.url) {
            // get authentication principal
            val userLoginPrincipal = call.authentication.principal as AuthService.UserLoginPrincipal
            // add session to logged user
            call.sessions.set(
                    SessionLogin(
                        userId = userLoginPrincipal.userId,
                        userName = userLoginPrincipal.userName
                    )
            )
            // show done page
            call.respondHtmlTemplate(
                    DoneTemplate(
                            doneTitle = "Access secure area: ${userLoginPrincipal.userName}",
                            onContinueRoute = Endpoint.HOME
                    )
            ) { }
        }
    }
}