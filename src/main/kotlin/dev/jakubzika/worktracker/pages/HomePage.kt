package dev.jakubzika.worktracker.pages

import dev.jakubzika.worktracker.APP_NAME
import dev.jakubzika.worktracker.pages.template.MainTemplate
import dev.jakubzika.worktracker.routing.Endpoint
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import kotlinx.html.*

const val HOME_PAGE_TITLE = "Welcome to $APP_NAME"

fun Routing.homePage(endpoint: Endpoint = Endpoint.HOME) {
    get(endpoint.url) {
        call.respondHtmlTemplate(
                if (true) {
                    unauthorized()
                } else {
                    mainPage()
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

private fun mainPage(main: MainTemplate = MainTemplate()) = object : Template<HTML> {
    override fun HTML.apply() {
        insert(main) {
            content {
                p {
                    +"Main Page !!!"
                }
            }
        }
    }
}