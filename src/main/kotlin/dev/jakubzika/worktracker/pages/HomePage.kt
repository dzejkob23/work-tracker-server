package dev.jakubzika.worktracker.pages

import dev.jakubzika.worktracker.APP_NAME
import dev.jakubzika.worktracker.routing.HOME
import dev.jakubzika.worktracker.routing.LOGIN
import dev.jakubzika.worktracker.routing.PAGE_TITLE
import dev.jakubzika.worktracker.routing.REGISTRATION
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.routing.Routing
import io.ktor.routing.get
import kotlinx.html.*

const val HOME_PAGE_TITLE = "Welcome to $APP_NAME"

fun Routing.homePage() {
    get(HOME) {
        call.respondHtml {
            head {
                title(PAGE_TITLE)
            }
            body {
                h1 { +HOME_PAGE_TITLE }
                p {
                    + "If you have not an account already. Please, click to \"Register\" button."
                    br
                    form(
                            action = REGISTRATION,
                            encType = FormEncType.textPlain,
                            method = FormMethod.get
                    ) {
                        submitInput { value = "Register" }
                    }
                }
                p {
                    + "On the other side click on \"Login\" button."
                    br
                    form(
                            action = LOGIN,
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