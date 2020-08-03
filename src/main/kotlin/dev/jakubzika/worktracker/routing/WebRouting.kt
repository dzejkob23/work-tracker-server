package dev.jakubzika.worktracker.routing

import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import kotlinx.html.*

const val HOME = "/"
const val LOGIN = "/login"

fun Routing.web() {
    get(HOME) {
        call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
    }
    get(LOGIN) {
        call.respondHtml {
            head {
                title { +"Ktor: html" }
            }
            body {
                p {
                    +"Hello from Ktor html sample application"
                }
                widget {
                    +"Widgets are just functions"
                }
                loginForm()
            }
        }
    }
}

@HtmlTagMarker
fun FlowContent.widget(body: FlowContent.() -> Unit) {
    div { body() }
}

fun FlowContent.loginForm() {
    form {
        label {
            +"Nick Name: "
            input {
                id = "nicknameInputId"
                name = "nicknameLabelId"
                type = InputType.text
            }
        }
        br
        label {
            +"Password: "
            input {
                id = "passwordInputId"
                name = "passwordLabelId"
                type = InputType.password
            }
        }
        br
        button {
            +"Login"
        }
    }
}