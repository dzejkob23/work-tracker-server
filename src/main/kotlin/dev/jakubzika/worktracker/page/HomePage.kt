package dev.jakubzika.worktracker.page

import dev.jakubzika.worktracker.APP_NAME
import dev.jakubzika.worktracker.routing.Endpoint
import dev.jakubzika.worktracker.routing.PAGE_TITLE
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.Routing
import io.ktor.routing.get
import kotlinx.html.*

const val HOME_PAGE_TITLE = "Welcome to $APP_NAME"

fun Routing.homePage(endpoint: Endpoint = Endpoint.HOME) {
    get(endpoint.url) {
        call.respondHtml {
            head {
                title(PAGE_TITLE)
            }
            body {
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