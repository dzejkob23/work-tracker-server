package dev.jakubzika.worktracker.page

import dev.jakubzika.worktracker.auth.isUserAuthorized
import dev.jakubzika.worktracker.page.template.MainTemplate
import dev.jakubzika.worktracker.routing.Endpoint
import io.ktor.application.*
import io.ktor.html.*
import io.ktor.routing.*
import kotlinx.html.*

fun Routing.homePage(endpoint: Endpoint = Endpoint.HOME) {
    get(endpoint.url) {
        call.respondHtmlTemplate(
                if (isUserAuthorized()) {
                    mainView()
                } else {
                    unauthorizedView()
                }
        ) {}
    }
}

private fun unauthorizedView(main: MainTemplate = MainTemplate()) = object : Template<HTML> {
    override fun HTML.apply() {
        insert(main) {
            content {
                div(classes = "flex-vertical") {
                    h1 { +"Track time easily as possible..." }
                    p {
                        + "If you have not an account already. Please, click to \"Register\" button."
                        + "On the other side click on \"Login\" button."
                    }
                    form(
                            action = Endpoint.REGISTRATION.url,
                            encType = FormEncType.textPlain,
                            method = FormMethod.get
                    ) {
                        submitInput(classes = "buttonPrimary") { value = "Register" }
                    }
                    form(
                            action = Endpoint.LOGIN.url,
                            encType = FormEncType.textPlain,
                            method = FormMethod.get
                    ) {
                        submitInput(classes = "buttonPrimary") { value = "Login" }
                    }
                }
            }
        }
    }
}

private fun mainView(main: MainTemplate = MainTemplate()) = object : Template<HTML> {
    override fun HTML.apply() {
        insert(main) {
            headers {
                script { src = "/static/js/utils.js" }
            }
            content {
                div(classes = "flex-horizontal") {
                    section(classes = "card") {
                        h2 { +"Měření" }
                        p { +"00h 00m 00s" }
                        button(classes = "buttonPrimary") { +"Start" }
                        button(classes = "buttonPrimary") { +"Stop & Save" }
                        button(classes = "buttonPrimary") { +"Restart" }
                    }
                    section(classes = "card") {
                        h2 { +"Profil" }
                        p { +"Jakub Zíka" }
                        p { +"Nastavení" }
                        p {
                            form(
                                    action = Endpoint.LOGOUT.url,
                                    encType = FormEncType.textPlain,
                                    method = FormMethod.get
                            ) {
                                submitInput(classes = "buttonPrimary") { value = "Logout" }
                            }
                        }
                    }
                    section(classes = "card") {
                        h2 { +"Statistiky" }
                        h3 { +"Denní" }
                        h3 { +"Týdenní" }
                        h3 { +"Měsíční" }
                        h3 { +"Roční" }
                    }
                }
            }
        }
    }
}