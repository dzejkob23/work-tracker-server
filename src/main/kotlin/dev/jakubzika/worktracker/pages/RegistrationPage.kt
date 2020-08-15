package dev.jakubzika.worktracker.pages

import dev.jakubzika.worktracker.routing.FORM_FIELD_NAME
import dev.jakubzika.worktracker.routing.FORM_FIELD_PASSWD
import dev.jakubzika.worktracker.routing.REGISTRATION
import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.routing.Route
import io.ktor.routing.get
import io.ktor.routing.route
import kotlinx.html.*

fun Route.registrationPage(route: String) {
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
    }
}

fun FlowContent.registrationForm() {
    form(
            action = REGISTRATION,
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
            passwordInput(name = FORM_FIELD_PASSWD)
        }
        p {
            submitInput { value = "Login" }
        }
    }
}