package dev.jakubzika.worktracker.page

import dev.jakubzika.worktracker.APP_NAME
import dev.jakubzika.worktracker.auth.SessionLogin
import dev.jakubzika.worktracker.auth.isUserAuthorized
import dev.jakubzika.worktracker.page.template.MainTemplate
import dev.jakubzika.worktracker.routing.Endpoint
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import io.ktor.sessions.*
import kotlinx.html.*

const val HOME_PAGE_TITLE = "Welcome to $APP_NAME"

fun Routing.homePage(endpoint: Endpoint = Endpoint.HOME) {
    get(endpoint.url) {
        call.respondHtmlTemplate(
                if (isUserAuthorized()) {
                    mainView()
                } else {
                    unauthorized()
                }
        ) {}
    }
}

private fun unauthorized(main: MainTemplate = MainTemplate()) = object : Template<HTML> {
    override fun HTML.apply() {
        insert(main) {
            content {
                h1 { +HOME_PAGE_TITLE }
                p {
                    + "If you have not an account already. Please, click to \"Register\" button."
                    + "On the other side click on \"Login\" button."
                }
                p {
                    form(
                            action = Endpoint.REGISTRATION.url,
                            encType = FormEncType.textPlain,
                            method = FormMethod.get
                    ) {
                        submitInput { value = "Register" }
                    }
                }
                p {
                    form(
                            action = Endpoint.LOGIN.url,
                            encType = FormEncType.textPlain,
                            method = FormMethod.get
                    ) {
                        submitInput { value = "Login" }
                    }
                }
            }
        }
    }
}

private fun mainView(main: MainTemplate = MainTemplate()) = object : Template<HTML> {
    override fun HTML.apply() {
        insert(main) {
            content {
                p {
                    form(
                            action = Endpoint.LOGOUT.url,
                            encType = FormEncType.textPlain,
                            method = FormMethod.get
                    ) {
                        submitInput { value = "Logout" }
                    }
                }
            }
        }
    }
}